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
import static com.radiant.acsl.myworkapp.Other.TallyDb.FLD_LEDGER_SHORT;
import static com.radiant.acsl.myworkapp.Other.TallyDb.FLD_LEDGER_TYPE;
import static com.radiant.acsl.myworkapp.Other.TallyDb.FLD_VOUCHER_AMOUNT;
import static com.radiant.acsl.myworkapp.Other.TallyDb.FLD_VOUCHER_BILL;
import static com.radiant.acsl.myworkapp.Other.TallyDb.FLD_VOUCHER_CREDIT;
import static com.radiant.acsl.myworkapp.Other.TallyDb.FLD_VOUCHER_DATE;
import static com.radiant.acsl.myworkapp.Other.TallyDb.FLD_VOUCHER_EXPORT;
import static com.radiant.acsl.myworkapp.Other.TallyDb.FLD_VOUCHER_LEDGER;
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
        values.put(FLD_ID, ledger.getId());
        values.put(FLD_LEDGER_NAME, ledger.getAcctName());
        values.put(FLD_LEDGER_SHORT, ledger.getAcctShort());
        values.put(FLD_LEDGER_BILL, ledger.getIsBankCash());
        values.put(FLD_LEDGER_TYPE, ledger.getIsBillWise());

        dbObj.insertWithOnConflict(TBL_LEDGER, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        return true;
    }

    public static boolean addVoucher(TallyDb db, VoucherMain voucherMain, ArrayList<Voucher> arrVoucher) {

        SQLiteDatabase dbObj = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FLD_VOUCHER_TYPE, voucherMain.getVoucherType());
        values.put(FLD_VOUCHER_EXPORT, voucherMain.getisExported());
        values.put(FLD_VOUCHER_DATE, voucherMain.getPostDate());

        int vchId = ((int) (dbObj.insertWithOnConflict(TBL_VOUCHER, null, values, SQLiteDatabase.CONFLICT_REPLACE)));
        Log.i("Vch List Count", String.valueOf(arrVoucher.size()));
        for (Voucher voucher : arrVoucher) {
            ContentValues values1 = new ContentValues();
            values1.put(FLD_ID, vchId);
            values1.put(FLD_VOUCHER_LEDGER, voucher.getLedgerName());
            values1.put(FLD_VOUCHER_BILL, voucher.getRef());
            values1.put(FLD_VOUCHER_AMOUNT, voucher.getDblAmount());
            values1.put(FLD_VOUCHER_CREDIT, voucher.getIsCredit());
            dbObj.insertWithOnConflict(TBL_ENTRY, null, values1, SQLiteDatabase.CONFLICT_REPLACE);
        }
        return true;
    }
}
