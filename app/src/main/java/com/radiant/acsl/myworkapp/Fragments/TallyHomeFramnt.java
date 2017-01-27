package com.radiant.acsl.myworkapp.Fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.radiant.acsl.myworkapp.Adapters.VoucherListAdapter;
import com.radiant.acsl.myworkapp.Adapters.VouchersAdapter;
import com.radiant.acsl.myworkapp.Modals.Voucher;
import com.radiant.acsl.myworkapp.Modals.VoucherMain;
import com.radiant.acsl.myworkapp.Other.Constants;
import com.radiant.acsl.myworkapp.Other.DbAdapter;
import com.radiant.acsl.myworkapp.Other.PopulateDb;
import com.radiant.acsl.myworkapp.Other.TallyDb;
import com.radiant.acsl.myworkapp.R;

import org.xmlpull.v1.XmlSerializer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class TallyHomeFramnt extends Fragment {


    private ListView listView;
    private VoucherListAdapter arrayAdapter;
    private VouchersAdapter<VoucherMain> arrayAdapter1;
    private ArrayList<VoucherMain> voucherMains;
    private ArrayList<Voucher> voucherArrayList;
    private TallyDb tallyDb;
    private DbAdapter dbAdapter;

    public TallyHomeFramnt() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tally_home_framnt, container, false);

        tallyDb = TallyDb.getInstance(getActivity());
        listView = (ListView) view.findViewById(R.id.listManager);
        voucherMains = dbAdapter.getInstance().getVoucherList(tallyDb);
        Log.i("Count of voucher", String.valueOf(voucherMains.size()));
        arrayAdapter1 = new VouchersAdapter<VoucherMain>(getActivity(), voucherMains);
        listView.setAdapter(arrayAdapter1);
        return view;
    }

    private void tallyXmlGenerate() {
        boolean iFlag = false;
        ArrayList<VoucherMain> mainArrayList = arrayAdapter1.getCheckedItems();
        Log.i("Checked Items", "Selected Items count is " + mainArrayList.size());

        for (int i = 0; i <= mainArrayList.size() - 1; i++) {
            VoucherMain main = (VoucherMain) mainArrayList.get(i);
            Log.i("Vch Main", "ID: " + String.valueOf(main.getId())
                    + " ; Type: " + String.valueOf(main.getVoucherType())
                    + " ; Date: " + String.valueOf(main.getPostDate())
                    + " ; IsExport: " + String.valueOf((boolean) main.getisExported()));

            ArrayList<Voucher> vouchers = DbAdapter.getInstance().getVoucher(tallyDb, main.getId());
            for (Voucher vch : vouchers) {
                Log.i("Vch Sub", "ID:" + String.valueOf(main.getId())
                        + " ; Ledger: " + String.valueOf(vch.getLedgerName())
                        + " ; Amount: " + String.valueOf(vch.getDblAmount())
                        + " ; IsCredit: " + String.valueOf((boolean) vch.getIsCredit()));

            }
        }

        XmlSerializer serializer = Xml.newSerializer();
        FileOutputStream fos = null;
//                try {
//                    fos = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/test.xml");
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//                BufferedWriter writer1 = new BufferedWriter(new OutputStreamWriter(fos));
        StringWriter writer = new StringWriter();

        try {
            serializer.setOutput(writer);
            serializer.startDocument("", true);

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

            for (int i = 0; i <= mainArrayList.size() - 1; i++) {

                VoucherMain main = (VoucherMain) mainArrayList.get(i);

                serializer.startTag("", "TALLYMESSAGE");
                serializer.startTag("", "VOUCHER");
                serializer.attribute("", "VCHTYPE", main.getVoucherType());

                serializer.attribute("", "ACTION", "Create");
                DateFormat source = new SimpleDateFormat("dd-MM-yyyy");
                Date postDate = null;
                try {
                    postDate = source.parse(main.getPostDate().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat spl = new SimpleDateFormat("yyyyMMdd");
                String sDate = spl.format(postDate);
                serializer.startTag("", "DATE");
                serializer.text(sDate);
                serializer.endTag("", "DATE");

                serializer.startTag("", "NARRATION");
                serializer.text(TextUtils.isEmpty(main.getNarration()) ? "" : main.getNarration());
                serializer.endTag("", "NARRATION");

                serializer.startTag("", "VOUCHERTYPENAME");
                serializer.text(main.getVoucherType());
                serializer.endTag("", "VOUCHERTYPENAME");

                serializer.startTag("", "VOUCHERNUMBER");
                serializer.text(String.valueOf(i));
                serializer.endTag("", "VOUCHERNUMBER");

                serializer.startTag("", "EFFECTIVEDATE");
                serializer.text(sDate);
                serializer.endTag("", "EFFECTIVEDATE");

                ArrayList<Voucher> vouchers = DbAdapter.getInstance().getVoucher(tallyDb, main.getId());

                for (Voucher vch : vouchers) {

                    serializer.startTag("", "ALLLEDGERENTRIES.LIST");
                    String[] values = getVoucherValues(main.getVoucherType(), vch.getDblAmount(), (boolean) vch.getIsCredit());

                    serializer.startTag("", "LEDGERNAME");
                    serializer.text(vch.getLedgerName());
                    serializer.endTag("", "LEDGERNAME");

                    serializer.startTag("", "LEDGERFROMITEM");
                    serializer.text(Constants.NO);
                    serializer.endTag("", "LEDGERFROMITEM");

                    serializer.startTag("", "ISDEEMEDPOSITIVE");
                    serializer.text(values[1]);
                    serializer.endTag("", "ISDEEMEDPOSITIVE");

                    serializer.startTag("", "AMOUNT");
                    serializer.text(values[0]);
                    serializer.endTag("", "AMOUNT");

                    if (vch.getRef() != null && vch.getRef() != "") {
                        serializer.startTag("", "BILLALLOCATIONS.LIST");
                        serializer.startTag("", "NAME");
                        serializer.text(String.valueOf(vch.getRef()));
                        serializer.endTag("", "NAME");

                        serializer.startTag("", "BILLTYPE");
                        serializer.text("New Ref");
                        serializer.endTag("", "BILLTYPE");

                        serializer.startTag("", "AMOUNT");
                        serializer.text(values[0]);
                        serializer.endTag("", "AMOUNT");

                        serializer.endTag("", "BILLALLOCATIONS.LIST");
                    }

                    serializer.endTag("", "ALLLEDGERENTRIES.LIST");
                }
                serializer.endTag("", "VOUCHER");
                serializer.endTag("", "TALLYMESSAGE");
            }

            serializer.endTag("", "REQUESTDATA");
            serializer.endTag("", "IMPORTDATA");
            serializer.endTag("", "BODY");
            serializer.endTag("", "ENVELOP");
            serializer.endDocument();
            serializer.flush();

        } catch (IOException e) {

            e.printStackTrace();
        }

//                Log.i("String Writer", writer.toString());
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            try {
                fos = new FileOutputStream(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/test.xml");
//                    FileOutputStream fos=getApplicationContext().openFileOutput("test.xml", Context.MODE_PRIVATE);
//                        Log.i("String Writer", writer.toString());
                fos.write(writer.toString().getBytes());
                fos.close();
                iFlag = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (iFlag) {
                for (int i = 0; i <= mainArrayList.size() - 1; i++) {
                    VoucherMain main = (VoucherMain) mainArrayList.get(i);
                    main.setIsExported(true);
                    PopulateDb.updateVoucherStatus(tallyDb, main);
                }
                Toast.makeText(getActivity(), "XML File Exported", Toast.LENGTH_LONG);
            }
        }
    }

    private String[] getVoucherValues(String vchType, double dboAmount, boolean isCredit) {
        String[] strings = new String[2];

        switch (vchType) {

            case ("Payment"):
                if (isCredit) {
                    strings[0] = String.valueOf(dboAmount);
                    strings[1] = "No";
                } else {
                    strings[0] = String.valueOf(dboAmount * (-1));
                    strings[1] = "Yes";
                }
                break;
            case ("Receipt"):
                if (isCredit) {
                    strings[0] = String.valueOf(dboAmount);
                    strings[1] = "No";
                } else {
                    strings[0] = String.valueOf(dboAmount * (-1));
                    strings[1] = "Yes";
                }
                break;

            case ("Journal"):
                if (isCredit) {
                    strings[0] = String.valueOf(dboAmount);
                    strings[1] = "No";
                } else {
                    strings[0] = String.valueOf(dboAmount * (-1));
                    strings[1] = "Yes";
                }
                break;


        }

        return strings;


    }

}
