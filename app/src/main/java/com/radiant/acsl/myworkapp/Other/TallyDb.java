package com.radiant.acsl.myworkapp.Other;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sakthivel on 10/01/2017.
 */

public class TallyDb extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "tallydb";
    private static int DATABASE_VERSION = 2;

    private static TallyDb sInstance;

    //Tables List
    protected static final String TBL_VOUCHER = "tbl_voucher";
    protected static final String TBL_ENTRY = "tbl_entry";
    protected static final String TBL_LEDGER = "tbl_ledger";

    //Table Fields
    public static String FLD_ID = "id";

    protected static final String FLD_VOUCHER_TYPE = "voucher_type";
    protected static final String FLD_VOUCHER_DATE = "post_date";
    protected static final String FLD_VOUCHER_EXPORT = "is_exported";
    protected static final String FLD_VOUCHER_REF = "narration";

    protected static final String FLD_LEDGER_NAME = "acct_name";
    protected static final String FLD_LEDGER_SHORT = "acct_short";
    protected static final String FLD_LEDGER_TYPE = "is_bank_case";
    protected static final String FLD_LEDGER_BILL = "is_billwise";

    protected static final String FLD_VOUCHER_LEDGER = "acct_name";
    protected static final String FLD_VOUCHER_BILL_REF = "bill_ref";
    protected static final String FLD_VOUCHER_CREDIT = "is_credit";
    protected static final String FLD_VOUCHER_AMOUNT = "amount";

//Query to create table

    private static String CREATE_TBL_LEDGER = "CREATE TABLE " + TBL_LEDGER + "("
            + FLD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FLD_LEDGER_NAME + " TEXT, "
            + FLD_LEDGER_SHORT + " TEXT, "
            + FLD_LEDGER_TYPE + " TEXT, "
            + FLD_LEDGER_BILL + " INTEGER "
            + ")";

    private static String CREATE_TBL_VOUCHER = "CREATE TABLE " + TBL_VOUCHER + "("
            + FLD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FLD_VOUCHER_TYPE + " TEXT, "
            + FLD_VOUCHER_DATE + " TEXT, "
            + FLD_VOUCHER_EXPORT + " INTEGER, "
            + FLD_VOUCHER_REF + " TEXT "
            + ")";

    private static String CREATE_TBL_ENTRY = "CREATE TABLE " + TBL_ENTRY + "("
            + FLD_ID + " INTEGER , "
            + FLD_VOUCHER_LEDGER + " TEXT, "
            + FLD_VOUCHER_BILL_REF + " TEXT, "
            + FLD_VOUCHER_CREDIT + " INTEGER, "
            + FLD_VOUCHER_AMOUNT + " REAL "
            + ")";

    public TallyDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public TallyDb(Context context, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public TallyDb(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }


    public static synchronized TallyDb getInstance(Context context) {

        if (sInstance == null) {
            sInstance = new TallyDb(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TBL_ENTRY);
        db.execSQL(CREATE_TBL_VOUCHER);
        db.execSQL(CREATE_TBL_LEDGER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            switch (oldVersion) {
                case 1:
                    db.execSQL("alter table " + TBL_VOUCHER + " add coulmn " + FLD_VOUCHER_REF + " text ");
                    //onCreate(db);
                    break;
                case 2:
//                    db.execSQL("DROP TABLE  IF EXISTS" + TBL_LEDGER);
//                    db.execSQL("DROP TABLE  IF EXISTS" + TBL_VOUCHER);
//                    db.execSQL("DROP TABLE  IF EXISTS" + TBL_ENTRY);
                    //onCreate(db);
                    break;
                default:
                    break;
            }
        } else {


        }
    }
}
