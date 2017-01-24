package com.radiant.acsl.myworkapp.Activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.Xml;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.itextpdf.awt.geom.CubicCurve2D;
import com.radiant.acsl.myworkapp.Adapters.VoucherListAdapter;
import com.radiant.acsl.myworkapp.Adapters.VoucherListAdapter1;
import com.radiant.acsl.myworkapp.Modals.Voucher;
import com.radiant.acsl.myworkapp.Modals.VoucherMain;
import com.radiant.acsl.myworkapp.Other.Constants;
import com.radiant.acsl.myworkapp.Other.DbAdapter;
import com.radiant.acsl.myworkapp.Other.TallyDb;
import com.radiant.acsl.myworkapp.R;

import org.xmlpull.v1.XmlSerializer;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sakthivel on 11/01/2017.
 */

public class AccountHome extends AppCompatActivity {

    private FloatingActionButton fab;
    private ListView listView;
    private VoucherListAdapter arrayAdapter;
    private VoucherListAdapter1<VoucherMain> arrayAdapter1;
    private ArrayList<VoucherMain> voucherMains;
    private ArrayList<Voucher> voucherArrayList;
    private TallyDb tallyDb;

    private DbAdapter dbAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tallyDb = TallyDb.getInstance(this);
        listView = (ListView) findViewById(R.id.listManager);
        voucherMains = dbAdapter.getInstance().getVoucherList(tallyDb);


//        Type 1 -
//        arrayAdapter = new VoucherListAdapter(this, voucherMains);
//        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//        listView.setAdapter(arrayAdapter);


//        Type 2 -
        Log.i("Count of voucher", String.valueOf(voucherMains.size()));
        arrayAdapter1 = new VoucherListAdapter1<VoucherMain>(this, voucherMains);
        listView.setAdapter(arrayAdapter1);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<VoucherMain> mainArrayList = arrayAdapter1.getCheckedItems();
                Log.i("Checked Items", "Selected Items count is " + mainArrayList.size());

//                for (int i = 0; i <= mainArrayList.size() - 1; i++) {
//                    VoucherMain main = (VoucherMain) mainArrayList.get(i);
//                    Log.i("Vch Main", "ID: " + String.valueOf(main.getId())
//                            + " ; Type: " + String.valueOf(main.getVoucherType())
//                            + " ; Date: " + String.valueOf(main.getPostDate())
//                            + " ; IsExport: " + String.valueOf(main.getisExported()));
//
//                    ArrayList<Voucher> vouchers = DbAdapter.getInstance().getVoucher(tallyDb, main.getId());
//                    for (Voucher vch : vouchers) {
//                        Log.i("Vch Sub", "ID:" + String.valueOf(main.getId())
//                                + " ; Ledger: " + String.valueOf(vch.getLedgerName())
//                                + " ; Amount: " + String.valueOf(vch.getDblAmount())
//                                + " ; IsCredit: " + String.valueOf(vch.getIsCredit()));
//
//                    }
//                }

                XmlSerializer serializer = Xml.newSerializer();
                StringWriter writer = new StringWriter();

                try {
                    serializer.setOutput(writer);
                    serializer.startDocument("UTF-8", true);

                    serializer.startTag("", "ENVELOP");
                    serializer.startTag("", "HEADER");
                    serializer.startTag("", "TALLYREQUEST");
                    serializer.text("Import Data");
                    serializer.endTag("", "TALLYREQUEST");
                    serializer.endTag("", "HEADER");
                    serializer.startTag("", "BODY");

                    serializer.startTag("", "IMPORTDATA");
                    serializer.startTag("", "REQUESTDESC");
                    serializer.startTag("", "REPORTNAME");
                    serializer.text("Vouchers");
                    serializer.endTag("", "REPORTNAME");

                    serializer.startTag("", "STATICVARIABLES");
                    serializer.startTag("", "SVCURRENTCOMPANY");
                    serializer.text("Radiant");
                    serializer.endTag("", "SVCURRENTCOMPANY");
                    serializer.endTag("", "STATICVARIABLES");
                    serializer.endTag("", "REQUESTDESC");

                    serializer.startTag("", "REQUESTDATA");

                    for (int i = 0; i < mainArrayList.size() - 1; i++) {

                        VoucherMain main = (VoucherMain) mainArrayList.get(i);

                        serializer.startTag("", "TALLYMESSAGE");
                        serializer.startTag("", "VOUCHER");

                        serializer.startTag("", "DATE");
                        serializer.text(main.getPostDate());
                        serializer.endTag("", "DATE");

                        serializer.startTag("", "NARRATION");
                        serializer.text(main.getNarration());
                        serializer.endTag("", "NARRATION");

                        serializer.startTag("", "VOUCHERTYPENAME");
                        serializer.text(main.getVoucherType());
                        serializer.endTag("", "VOUCHERTYPENAME");

                        serializer.startTag("", "VOUCHERNUMBER");
                        serializer.text(String.valueOf(i));
                        serializer.endTag("", "VOUCHERNUMBER");

                        serializer.startTag("", "PARTYLEDGERNAME");
                        serializer.text(Constants.NO);
                        serializer.endTag("", "PARTYLEDGERNAME");

                        serializer.startTag("", "CSTFORMISSUETYPE");
                        serializer.endTag("", "CSTFORMISSUETYPE");

                        serializer.startTag("", "CSTFORMRECVTYPE");
                        serializer.endTag("", "CSTFORMRECVTYPE");

                        serializer.startTag("", "FBTPAYMENTTYPE");
                        serializer.text("Default");
                        serializer.endTag("", "FBTPAYMENTTYPE");

                        serializer.startTag("", "DIFFACTUALQTY");
                        serializer.endTag("", "DIFFACTUALQTY");

                        serializer.startTag("", "AUDITED");
                        serializer.endTag("", "AUDITED");

                        serializer.startTag("", "FORJOBCOSTING");
                        serializer.text("");
                        serializer.endTag("", "FORJOBCOSTING");

                        serializer.startTag("", "ISOPTIONAL");
                        serializer.endTag("", "ISOPTIONAL");

                        serializer.startTag("", "EFFECTIVEDATE");
                        serializer.text(main.getPostDate());
                        serializer.endTag("", "EFFECTIVEDATE");

                        serializer.startTag("", "USEFORINTEREST");
                        serializer.text("");
                        serializer.endTag("", "USEFORINTEREST");

                        serializer.startTag("", "USEFORGAINLOSS");
                        serializer.text("");
                        serializer.endTag("", "USEFORGAINLOSS");

                        serializer.startTag("", "USEFORGODOWNTRANSFER");
                        serializer.text("");
                        serializer.endTag("", "USEFORGODOWNTRANSFER");

                        serializer.startTag("", "USEFORCOMPOUND");
                        serializer.text("");
                        serializer.endTag("", "USEFORCOMPOUND");

                        serializer.startTag("", "EXCISEOPENING");
                        serializer.text("");
                        serializer.endTag("", "EXCISEOPENING");

                        serializer.startTag("", "ISCANCELLED");
                        serializer.text("");
                        serializer.endTag("", "ISCANCELLED");

                        serializer.startTag("", "HASCASHFLOW");
                        serializer.text("");
                        serializer.endTag("", "HASCASHFLOW");

                        serializer.startTag("", "ISPOSTDATED");
                        serializer.text(Constants.NO);
                        serializer.endTag("", "ISPOSTDATED");

                        serializer.startTag("", "USETRACKINGNUMBER");
                        serializer.text("");
                        serializer.endTag("", "USETRACKINGNUMBER");

                        serializer.startTag("", "ISINVOICE");
                        serializer.text("");
                        serializer.endTag("", "ISINVOICE");

                        ArrayList<Voucher> vouchers = DbAdapter.getInstance().getVoucher(tallyDb, main.getId());

                        for (Voucher vch : vouchers) {

                            serializer.startTag("", "ALLLEDGERENTRIES.LIST");

                            serializer.startTag("", "LEDGERNAME");
                            serializer.text(vch.getLedgerName());
                            serializer.endTag("", "LEDGERNAME");

                            serializer.startTag("", "GSTCLASS");
                            serializer.text(Constants.NO);
                            serializer.endTag("", "GSTCLASS");

                            serializer.startTag("", "ISDEEMEDPOSITIVE");
                            serializer.text(Constants.NO);
                            serializer.endTag("", "ISDEEMEDPOSITIVE");

                            serializer.startTag("", "LEDGERFROMITEM");
                            serializer.text(Constants.NO);
                            serializer.endTag("", "LEDGERFROMITEM");

                            serializer.startTag("", "REMOVEZEROENTRIES");
                            serializer.text(Constants.NO);
                            serializer.endTag("", "REMOVEZEROENTRIES");

                            serializer.startTag("", "ISPARTYLEDGER");
                            serializer.text(Constants.NO);
                            serializer.endTag("", "ISPARTYLEDGER");

                            serializer.startTag("", "AMOUNT");
                            serializer.text(String.valueOf(vch.getDblAmount()));
                            serializer.endTag("", "AMOUNT");

                            serializer.startTag("", "USETRACKINGNUMBER");
                            serializer.text("");
                            serializer.endTag("", "USETRACKINGNUMBER");

                            serializer.endTag("", "ALLLEDGERENTRIES.LIST");
                        }
                        serializer.endTag("", "VOUCHER");
                        serializer.endTag("", "TALLYMESSAGE");

                    }


//                    serializer.endTag("","BODY");
//                    serializer.endTag("","BODY");

                    serializer.endTag("", "REQUESTDATA");
                    serializer.endTag("", "IMPORTDATA");
                    serializer.endTag("", "BODY");
                    serializer.endTag("", "ENVELOP");
                    serializer

                } catch (IOException e) {
                    e.printStackTrace();
                }

                Log.i("String Writer", writer.toString());
                if (ActivityCompat.checkSelfPermission(AccountHome.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AccountHome.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    try {
                        FileOutputStream fos = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/test.xml");
//                    FileOutputStream fos=getApplicationContext().openFileOutput("test.xml", Context.MODE_PRIVATE);
                        Log.i("String Writer", writer.toString());
                        fos.write(writer.toString().getBytes());
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.i("is Completed", "Yes");
                }
            }

        });
    }
}
