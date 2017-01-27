package com.radiant.acsl.myworkapp.Other;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.radiant.acsl.myworkapp.Modals.Ledger;
import com.radiant.acsl.myworkapp.Modals.Voucher;
import com.radiant.acsl.myworkapp.Modals.VoucherMain;

import static com.radiant.acsl.myworkapp.Other.TallyDb.FLD_ID;
import static com.radiant.acsl.myworkapp.Other.TallyDb.TBL_ENTRY;
import static com.radiant.acsl.myworkapp.Other.TallyDb.TBL_VOUCHER;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sakthivel on 11/01/2017.
 */

public class DbAdapter {
    private static DbAdapter ourInstance = new DbAdapter();

    public DbAdapter() {
    }


    public static DbAdapter getInstance() {
        return ourInstance;
    }


    public ArrayList<Ledger> getLedgers(TallyDb db) {
        ArrayList<Ledger> ledgers = new ArrayList<Ledger>();
        String qry = "SELECT * FROM " + TBL_VOUCHER;
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(qry, null);

        if (cursor.moveToFirst()) {

            do {
                Ledger ledger = new Ledger();
                ledger.setId(cursor.getInt(0));
                ledger.setAcctName(cursor.getString(1));
                ledger.setAcctShort(cursor.getString(0));
                ledger.setBillWise(cursor.getInt(0));
                ledger.setIsBankCash(cursor.getInt(0));

            } while (cursor.moveToNext());
        }

        return ledgers;
    }


    public ArrayList<VoucherMain> getVoucherList(TallyDb db) {
        ArrayList<VoucherMain> vouchers = new ArrayList<VoucherMain>();
        String qry = "SELECT * FROM " + TBL_VOUCHER;

        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery(qry, null);

        if (cursor.moveToFirst()) {

            do {
                VoucherMain vch = new VoucherMain();
                vch.setId(cursor.getInt(0));
                vch.setVoucherType(cursor.getString(1));
                vch.setPostDate(cursor.getString(2));
                int i = Integer.parseInt(cursor.getString(3));
                vch.setIsExported(i == 0 ? false : true);
                vch.setNarration(cursor.getString(4));

                vouchers.add(vch);
            } while (cursor.moveToNext());
        }
        return vouchers;
    }

    public ArrayList<Voucher> getAllVoucher(TallyDb db) {
        ArrayList<Voucher> vouchers = new ArrayList<Voucher>();
        String qry = "SELECT * FROM " + TBL_ENTRY;

        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(qry, null);

        if (cursor.moveToFirst()) {
            do {
                Voucher vch = new Voucher();
                vch.setId(cursor.getInt(0));
//                Log.i("Value from DB",cursor.getString(1));
                vch.setLedgerName(cursor.getString(1));
                vch.setRef(cursor.getString(2));
                int i = Integer.parseInt(cursor.getString(3));
                vch.setCredit(i == 0 ? false : true);
                vch.setDblAmount(Double.parseDouble(cursor.getString(4)));
                vouchers.add(vch);
            } while (cursor.moveToNext());
        }
        return vouchers;
    }

    public ArrayList<Voucher> getVoucher(TallyDb db, int keyId) {
        ArrayList<Voucher> vouchers = new ArrayList<Voucher>();
        String qry = "SELECT * FROM " + TBL_ENTRY + " WHERE " + FLD_ID + "=" + keyId;
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(qry, null);

        if (cursor.moveToFirst()) {
            do {
                Voucher vch = new Voucher();
                vch.setId(cursor.getInt(0));
//                Log.i("Value from DB",cursor.getString(1));
                vch.setLedgerName(cursor.getString(1));
                vch.setRef(cursor.getString(2));
                int i = Integer.parseInt(cursor.getString(3));
                vch.setCredit(i == 0 ? false : true);
                Log.i("Value from DB after", String.valueOf(vch.getIsCredit()));
                vch.setDblAmount(Double.parseDouble(cursor.getString(4)));
                vouchers.add(vch);
            } while (cursor.moveToNext());
        }
        return vouchers;
    }
}
