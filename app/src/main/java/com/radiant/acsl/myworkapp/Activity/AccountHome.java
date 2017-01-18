package com.radiant.acsl.myworkapp.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.Xml;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.itextpdf.awt.geom.CubicCurve2D;
import com.radiant.acsl.myworkapp.Adapters.VoucherListAdapter;
import com.radiant.acsl.myworkapp.Modals.Voucher;
import com.radiant.acsl.myworkapp.Modals.VoucherMain;
import com.radiant.acsl.myworkapp.Other.Constants;
import com.radiant.acsl.myworkapp.Other.DbAdapter;
import com.radiant.acsl.myworkapp.Other.TallyDb;
import com.radiant.acsl.myworkapp.R;

import org.xmlpull.v1.XmlSerializer;

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
        arrayAdapter = new VoucherListAdapter(this, voucherMains);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listView.setAdapter(arrayAdapter);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SparseBooleanArray sparseBooleanArray = listView.getCheckedItemPositions();

                Log.i("Test", String.valueOf(sparseBooleanArray.size()));

//                XmlSerializer serializer = Xml.newSerializer();
//                StringWriter writer = new StringWriter();

//                try {
//                    serializer.setOutput(writer);
//                    serializer.startDocument("UTF-8", true);
//
//                    serializer.startTag("", "ENVELOP");
//                    serializer.startTag("", "HEADER");
//                    serializer.startTag("", "TALLYREQUEST");
//                    serializer.text("Import Data");
//                    serializer.endTag("", "TALLYREQUEST");
//                    serializer.endTag("", "HEADER");
//                    serializer.startTag("", "BODY");
//
//                    serializer.startTag("", "IMPORTDATA");
//                    serializer.startTag("", "REQUESTDESC");
//                    serializer.startTag("", "REPORTNAME");
//                    serializer.text("All Masters");
//                    serializer.endTag("", "REPORTNAME");
//
//                    serializer.startTag("", "STATICVARIABLES");
//                    serializer.startTag("", "SVCURRENTCOMPANY");
//                    serializer.text("Radiant");
//                    serializer.endTag("", "SVCURRENTCOMPANY");
//                    serializer.endTag("", "STATICVARIABLES");
//                    serializer.endTag("", "REQUESTDESC");
//
//                    serializer.startTag("", "REQUESTDATA");
//
////                    for (int i = 0; i < listView.getAdapter().getCount(); i++) {
//                    for (int i = 0; i < sparseBooleanArray.size() - 1; i++) {
//
//                        if (sparseBooleanArray.valueAt(i) == true) {
//                            VoucherMain main = (VoucherMain) listView.getAdapter().getItem((int) sparseBooleanArray.keyAt(i));
//
//                            serializer.startTag("", "TALLYMESSAGE");
//                            serializer.startTag("", "VOUCHER");
//
//                            serializer.startTag("", "DATE");
//                            serializer.text(main.getPostDate());
//                            serializer.endTag("", "DATE");
//
//                            serializer.startTag("", "NARRATION");
//                            serializer.text(main.getNarration());
//                            serializer.endTag("", "NARRATION");
//
//                            serializer.startTag("", "VOUCHERTYPENAME");
//                            serializer.text(main.getVoucherType());
//                            serializer.endTag("", "VOUCHERTYPENAME");
//
//                            serializer.startTag("", "VOUCHERNUMBER");
//                            serializer.text(String.valueOf(i));
//                            serializer.endTag("", "VOUCHERNUMBER");
//
//                            serializer.startTag("", "PARTYLEDGERNAME");
//                            serializer.text(Constants.NO);
//                            serializer.endTag("", "PARTYLEDGERNAME");
//
//                            serializer.startTag("", "CSTFORMISSUETYPE");
//                            serializer.endTag("", "CSTFORMISSUETYPE");
//
//                            serializer.startTag("", "CSTFORMRECVTYPE");
//                            serializer.endTag("", "CSTFORMRECVTYPE");
//
//                            serializer.startTag("", "FBTPAYMENTTYPE");
//                            serializer.text("Default");
//                            serializer.endTag("", "FBTPAYMENTTYPE");
//
//                            serializer.startTag("", "DIFFACTUALQTY");
//                            serializer.endTag("", "DIFFACTUALQTY");
//
//                            serializer.startTag("", "AUDITED");
//                            serializer.endTag("", "AUDITED");
//
//                            serializer.startTag("", "FORJOBCOSTING");
//                            serializer.text("");
//                            serializer.endTag("", "FORJOBCOSTING");
//
//                            serializer.startTag("", "ISOPTIONAL");
//                            serializer.endTag("", "ISOPTIONAL");
//
//                            serializer.startTag("", "EFFECTIVEDATE");
//                            serializer.text(main.getPostDate());
//                            serializer.endTag("", "EFFECTIVEDATE");
//
//                            serializer.startTag("", "USEFORINTEREST");
//                            serializer.text("");
//                            serializer.endTag("", "USEFORINTEREST");
//
//                            serializer.startTag("", "USEFORGAINLOSS");
//                            serializer.text("");
//                            serializer.endTag("", "USEFORGAINLOSS");
//
//                            serializer.startTag("", "USEFORGODOWNTRANSFER");
//                            serializer.text("");
//                            serializer.endTag("", "USEFORGODOWNTRANSFER");
//
//                            serializer.startTag("", "USEFORCOMPOUND");
//                            serializer.text("");
//                            serializer.endTag("", "USEFORCOMPOUND");
//
//                            serializer.startTag("", "EXCISEOPENING");
//                            serializer.text("");
//                            serializer.endTag("", "EXCISEOPENING");
//
//                            serializer.startTag("", "ISCANCELLED");
//                            serializer.text("");
//                            serializer.endTag("", "ISCANCELLED");
//
//                            serializer.startTag("", "HASCASHFLOW");
//                            serializer.text("");
//                            serializer.endTag("", "HASCASHFLOW");
//
//                            serializer.startTag("", "ISPOSTDATED");
//                            serializer.text(Constants.NO);
//                            serializer.endTag("", "ISPOSTDATED");
//
//                            serializer.startTag("", "USETRACKINGNUMBER");
//                            serializer.text("");
//                            serializer.endTag("", "USETRACKINGNUMBER");
//
//                            serializer.startTag("", "ISINVOICE");
//                            serializer.text("");
//                            serializer.endTag("", "ISINVOICE");
//
//                            ArrayList<Voucher> vouchers = DbAdapter.getInstance().getVoucher(tallyDb, main.getId());
//
//                            for (Voucher vch : vouchers) {
//
//                                serializer.startTag("", "ALLLEDGERENTRIES.LIST");
//
//                                serializer.startTag("", "LEDGERNAME");
//                                serializer.text(vch.getLedgerName());
//                                serializer.endTag("", "LEDGERNAME");
//
//                                serializer.startTag("", "GSTCLASS");
//                                serializer.text(Constants.NO);
//                                serializer.endTag("", "GSTCLASS");
//
//                                serializer.startTag("", "ISDEEMEDPOSITIVE");
//                                serializer.text(Constants.NO);
//                                serializer.endTag("", "ISDEEMEDPOSITIVE");
//
//                                serializer.startTag("", "LEDGERFROMITEM");
//                                serializer.text(Constants.NO);
//                                serializer.endTag("", "LEDGERFROMITEM");
//
//                                serializer.startTag("", "REMOVEZEROENTRIES");
//                                serializer.text(Constants.NO);
//                                serializer.endTag("", "REMOVEZEROENTRIES");
//
//                                serializer.startTag("", "ISPARTYLEDGER");
//                                serializer.text(Constants.NO);
//                                serializer.endTag("", "ISPARTYLEDGER");
//
//                                serializer.startTag("", "AMOUNT");
//                                serializer.text(String.valueOf(vch.getDblAmount()));
//                                serializer.endTag("", "AMOUNT");
//
//                                serializer.startTag("", "USETRACKINGNUMBER");
//                                serializer.text("");
//                                serializer.endTag("", "USETRACKINGNUMBER");
//
//                                serializer.endTag("", "ALLLEDGERENTRIES.LIST");
//                            }
//                            serializer.endTag("", "VOUCHER");
//                            serializer.endTag("", "TALLYMESSAGE");
//                        }
//                    }
//
//
////                    serializer.endTag("","BODY");
////                    serializer.endTag("","BODY");
////                    serializer.endTag("","BODY");
//                    serializer.endTag("", "REQUESTDATA");
//                    serializer.endTag("", "BODY");
//                    serializer.endTag("", "ENVELOP");
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

//                try {
//                    FileWriter fileWriter = new FileWriter("/sdcard/test.xml");
//                    fileWriter.write(writer.toString());
//                    fileWriter.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        });



    }
}
