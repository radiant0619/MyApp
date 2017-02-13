package com.radiant.acsl.myworkapp.Other;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.radiant.acsl.myworkapp.Modals.Ledger;
import com.radiant.acsl.myworkapp.Modals.Voucher;
import com.radiant.acsl.myworkapp.Modals.VoucherMain;

import java.util.ArrayList;

import static com.radiant.acsl.myworkapp.Other.TallyDb.FLD_ID;
import static com.radiant.acsl.myworkapp.Other.TallyDb.FLD_LEDGER_BILL;
import static com.radiant.acsl.myworkapp.Other.TallyDb.FLD_LEDGER_NAME;
import static com.radiant.acsl.myworkapp.Other.TallyDb.FLD_LEDGER_TYPE;
import static com.radiant.acsl.myworkapp.Other.TallyDb.FLD_LEDGER_ISBANK;
import static com.radiant.acsl.myworkapp.Other.TallyDb.FLD_VOUCHER_AMOUNT;
import static com.radiant.acsl.myworkapp.Other.TallyDb.FLD_VOUCHER_BILL_REF;
import static com.radiant.acsl.myworkapp.Other.TallyDb.FLD_VOUCHER_CREDIT;
import static com.radiant.acsl.myworkapp.Other.TallyDb.FLD_VOUCHER_DATE;
import static com.radiant.acsl.myworkapp.Other.TallyDb.FLD_VOUCHER_EXPORT;
import static com.radiant.acsl.myworkapp.Other.TallyDb.FLD_VOUCHER_LEDGER;
import static com.radiant.acsl.myworkapp.Other.TallyDb.FLD_VOUCHER_REF;
import static com.radiant.acsl.myworkapp.Other.TallyDb.FLD_VOUCHER_TYPE;
import static com.radiant.acsl.myworkapp.Other.TallyDb.TBL_ENTRY;
import static com.radiant.acsl.myworkapp.Other.TallyDb.TBL_LEDGER;
import static com.radiant.acsl.myworkapp.Other.TallyDb.TBL_VOUCHER;

/**
 * Created by sakthivel on 10/01/2017.
 */

public class PopulateDb {

    private static PopulateDb ourInstance = new PopulateDb();

    public PopulateDb() {
    }

    public static PopulateDb getInstance() {
        return ourInstance;
    }

    public static boolean addLedger(TallyDb db, Ledger ledger) {

        SQLiteDatabase dbObj = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FLD_LEDGER_NAME, ledger.getAcctName());
        values.put(FLD_LEDGER_TYPE, ledger.getAcctShort());
        values.put(FLD_LEDGER_BILL, ledger.getIsBillWise());
        values.put(FLD_LEDGER_ISBANK, ledger.getIsBankCash());

        dbObj.insertWithOnConflict(TBL_LEDGER, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        return true;
    }

    public static boolean updateLedger(TallyDb db, Ledger ledger) {

        SQLiteDatabase dbObj = db.getWritableDatabase();
        int ledgerId = ledger.getId();
        ContentValues values = new ContentValues();
        values.put(FLD_LEDGER_NAME, ledger.getAcctName());
        values.put(FLD_LEDGER_TYPE, ledger.getAcctShort());
        values.put(FLD_LEDGER_BILL, ledger.getIsBillWise());
        values.put(FLD_LEDGER_ISBANK, ledger.getIsBankCash());
        dbObj.update(TBL_LEDGER, values, FLD_ID + "=?", new String[]{String.valueOf(ledgerId)});
        return true;
    }


    public static boolean addVoucher(TallyDb db, VoucherMain voucherMain, ArrayList<Voucher> arrVoucher) {

        SQLiteDatabase dbObj = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FLD_VOUCHER_TYPE, voucherMain.getVoucherType());
        values.put(FLD_VOUCHER_EXPORT, (voucherMain.getisExported()) ? 1 : 0);
        values.put(FLD_VOUCHER_DATE, voucherMain.getPostDate());
        values.put(FLD_VOUCHER_REF, voucherMain.getNarration());

        int vchId = ((int) (dbObj.insertWithOnConflict(TBL_VOUCHER, null, values, SQLiteDatabase.CONFLICT_REPLACE)));
        Log.i("Vch List Count", String.valueOf(arrVoucher.size()));
        for (Voucher voucher : arrVoucher) {
            ContentValues values1 = new ContentValues();
            values1.put(FLD_ID, vchId);
            values1.put(FLD_VOUCHER_LEDGER, voucher.getLedgerName());
            values1.put(FLD_VOUCHER_BILL_REF, voucher.getRef());
            values1.put(FLD_VOUCHER_AMOUNT, voucher.getDblAmount());
            values1.put(FLD_VOUCHER_CREDIT, (voucher.getIsCredit()) ? 1 : 0);
//            Log.i("Entry to db", "After " + ((voucher.getIsCredit()) ? "1" : "0") + " Before " + voucher.getIsCredit());
            dbObj.insertWithOnConflict(TBL_ENTRY, null, values1, SQLiteDatabase.CONFLICT_REPLACE);
        }
        return true;
    }

    public static boolean updateVoucherStatus(TallyDb db, VoucherMain voucherMain) {

        SQLiteDatabase dbObj = db.getWritableDatabase();
        int vchId = voucherMain.getId();
        ContentValues values = new ContentValues();
        values.put(FLD_VOUCHER_TYPE, voucherMain.getVoucherType());
        values.put(FLD_VOUCHER_EXPORT, voucherMain.getisExported());
        values.put(FLD_VOUCHER_DATE, voucherMain.getPostDate());
        values.put(FLD_VOUCHER_REF, voucherMain.getNarration());
        dbObj.update(TBL_VOUCHER, values, FLD_ID + "=?", new String[]{String.valueOf(vchId)});
        return true;
    }

    public static boolean updateVoucher(TallyDb db, VoucherMain voucherMain, ArrayList<Voucher> arrVoucher) {

        SQLiteDatabase dbObj = db.getWritableDatabase();
        int vchId = voucherMain.getId();
        ContentValues values = new ContentValues();
        values.put(FLD_VOUCHER_TYPE, voucherMain.getVoucherType());
        values.put(FLD_VOUCHER_EXPORT, voucherMain.getisExported());
        values.put(FLD_VOUCHER_DATE, voucherMain.getPostDate());
        values.put(FLD_VOUCHER_REF, voucherMain.getNarration());
        dbObj.update(TBL_VOUCHER, values, FLD_ID + "=?", new String[]{String.valueOf(vchId)});

        dbObj.execSQL("delete from " + TBL_ENTRY + " where " + FLD_ID + " ='" + String.valueOf(vchId) + "'");

        for (Voucher voucher : arrVoucher) {
            ContentValues values1 = new ContentValues();
            values1.put(FLD_ID, vchId);
            values1.put(FLD_VOUCHER_LEDGER, voucher.getLedgerName());
            values1.put(FLD_VOUCHER_BILL_REF, voucher.getRef());
            values1.put(FLD_VOUCHER_AMOUNT, voucher.getDblAmount());
            values1.put(FLD_VOUCHER_CREDIT, voucher.getIsCredit());
            dbObj.insertWithOnConflict(TBL_ENTRY, null, values1, SQLiteDatabase.CONFLICT_REPLACE);
        }

        return true;
    }

    public static boolean Delete(TallyDb db, String table, String column, String value) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        sqLiteDatabase.delete(table, column + "=?", new String[]{value});
//        sqLiteDatabase.execSQL("DELETE FROM "+ table);

        return true;
    }

}
