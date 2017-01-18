package com.radiant.acsl.myworkapp.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.radiant.acsl.myworkapp.Modals.Voucher;
import com.radiant.acsl.myworkapp.Modals.VoucherMain;
import com.radiant.acsl.myworkapp.Other.PopulateDb;
import com.radiant.acsl.myworkapp.Other.TallyDb;
import com.radiant.acsl.myworkapp.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by sakthivel on 05/01/2017.
 */
public class Accounts extends AppCompatActivity implements View.OnClickListener {

    private TableLayout tblLayout;
    private Button btnCheck;
    private TextView txtDate;
    private Button btnSubmit;
    private FloatingActionButton fab;
    private ArrayAdapter<CharSequence> adapter;
    private boolean isChecked;

    private TallyDb dbTally;
    private PopulateDb populateDb;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voucher_layout);

        dbTally = TallyDb.getInstance(getApplicationContext());

        adapter = ArrayAdapter.createFromResource(Accounts.this, R.array.ledgers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tblLayout = (TableLayout) findViewById(R.id.tbl);
        isChecked = false;

        txtDate = (TextView) findViewById(R.id.dateValue);
        btnSubmit = (Button) findViewById(R.id.submit);

        txtDate.setText(String.valueOf(DateFormat.getDateInstance().format( new Date())));
        btnCheck = (Button) findViewById(R.id.btnCheck);
        btnSubmit.setEnabled(isChecked);
        btnSubmit.setOnClickListener(this);
        btnCheck.setOnClickListener(this);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                TableRow tf = new TableRow(Accounts.this);
//                Spinner spinner = new Spinner(Accounts.this);
//                spinner.setAdapter(adapter);
//                tf.addView(spinner);
//
//                Switch switch1 = new Switch(Accounts.this);
//                tf.addView(switch1);
//
//                EditText editText = new EditText(Accounts.this);
//                tf.addView(editText);
//
//                ImageButton imgBtn = new ImageButton(Accounts.this);
//
//                imgBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                    }
//                });
//                tf.addView(imgBtn);
                isChecked = false;
                btnSubmit.setEnabled(isChecked);
                TableRow tf = (TableRow) getLayoutInflater().inflate(R.layout.layout_tablerow, null);
                Spinner spin = (Spinner) tf.getChildAt(0);
                spin.setAdapter(adapter);
                tblLayout.addView(tf);
            }
        });

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.submit:
                Log.i("msg ", String.valueOf(tblLayout.getChildCount()));
                if (isChecked) {
                    Log.i("Main Count", "True ischecked");
                    if (tblLayout.getChildCount() > 2) {
                        Log.i("Main Count", "tbl Childs " + String.valueOf(tblLayout.getChildCount()));
                        ArrayList<Voucher> vch = new ArrayList<Voucher>();
                        VoucherMain vchMain = new VoucherMain();
                        vchMain.setIsExported(false);
                        vchMain.setPostDate(txtDate.getText().toString());
                        vchMain.setIsExported(false);
                        vchMain.setVoucherType("Payment");

                        for (int i = 1; i < tblLayout.getChildCount(); i++) {
                            TableRow tf1 = (TableRow) tblLayout.getChildAt(i);
                            Voucher voucher = new Voucher();
                            voucher.setLedgerName(((Spinner) tf1.getChildAt(0)).getSelectedItem().toString());
                            voucher.setCredit(((Switch) tf1.getChildAt(1)).isChecked());
                            voucher.setDblAmount(Double.parseDouble(((EditText) tf1.getChildAt(2)).getText().toString()));
                            vch.add(voucher);
//                            Log.i("msg ", ((Spinner) tf1.getChildAt(0)).getSelectedItem().toString());
//                            Log.i("msg ", String.valueOf(((Switch) tf1.getChildAt(1)).isChecked()));
//                            Log.i("msg ", ((EditText) tf1.getChildAt(2)).getText().toString());
                        }
                        Log.i("Main Count", String.valueOf(vch.size()));
                        boolean iFlag = PopulateDb.getInstance().addVoucher(dbTally, vchMain, vch);
                        if (iFlag) {
                            Intent intent = new Intent(Accounts.this, AccountHome.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Toast.makeText(this, "Pleae Add GLs", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btnCheck:
                Log.i("Status", String.valueOf(isChecked));
                isChecked = ValidateRows();
                btnSubmit.setEnabled(isChecked);
                Log.i("Status", String.valueOf(isChecked));
                break;
            case R.id.delete_row:

                TableRow vew = (TableRow) view.getParent();
//                Toast.makeText(getApplicationContext(), vew.getClass().getName(), Toast.LENGTH_SHORT).show();
                tblLayout.removeView(vew);
                isChecked = false;
                btnSubmit.setEnabled(isChecked);
                break;
        }
    }

    private boolean ValidateRows() {
        boolean isCredit = false;
        double dblDebit = 0;
        double dblCredit = 0;
        double value = 0;
        if (tblLayout.getChildCount() > 1) {
            for (int i = 1; i < tblLayout.getChildCount(); i++) {
                TableRow tf1 = (TableRow) tblLayout.getChildAt(i);
                value = ((EditText) tf1.getChildAt(2)).getText().toString().equals("") ? 0 : Double.parseDouble(((EditText) tf1.getChildAt(2)).getText().toString());

                if (isCredit == false && ((Switch) tf1.getChildAt(1)).isChecked()) {
                    isCredit = true;
                }
                if (((Switch) tf1.getChildAt(1)).isChecked() == false) {
                    dblDebit = dblDebit + value;
                } else {
                    dblCredit = dblCredit + value;
                }
            }
        }
        Log.i("Status", String.valueOf(isCredit) + "--> " + String.valueOf(dblDebit) + "--> " + String.valueOf(dblCredit));
        if (isCredit == true && Double.compare(dblDebit, dblCredit) == 0 && dblCredit > 0 && dblDebit > 0) {
            return true;
        } else if (isCredit == false) {
            Toast.makeText(getApplicationContext(), "Credit Ledger not available.", Toast.LENGTH_SHORT).show();
        } else if (Double.compare(dblDebit, dblCredit) != 0) {
            Toast.makeText(getApplicationContext(), "Dr/Cr Balance not matched.", Toast.LENGTH_SHORT).show();
        } else if (dblCredit <= 0 && dblDebit <= 0) {
            Toast.makeText(getApplicationContext(), "Dr/Cr Balance should greater than 0. ", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void getViewType(View view) {
        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;
            // do what you want with imageView
        } else if (view instanceof TextView) {
            TextView textView = (TextView) view;
            // do what you want with textView
        } else if (view instanceof TextView) {
            TextView textView = (TextView) view;
            // do what you want with textView
        } else if (view instanceof TextView) {
            TextView textView = (TextView) view;
            // do what you want with textView
        }
    }
}
