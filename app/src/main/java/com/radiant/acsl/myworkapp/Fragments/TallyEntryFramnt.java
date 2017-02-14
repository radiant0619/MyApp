package com.radiant.acsl.myworkapp.Fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.radiant.acsl.myworkapp.Activity.AccountHome;
import com.radiant.acsl.myworkapp.Activity.Accounts;
import com.radiant.acsl.myworkapp.Activity.TallyHome;
import com.radiant.acsl.myworkapp.Adapters.SpinnerAdapter;
import com.radiant.acsl.myworkapp.Modals.Ledger;
import com.radiant.acsl.myworkapp.Modals.Voucher;
import com.radiant.acsl.myworkapp.Modals.VoucherMain;
import com.radiant.acsl.myworkapp.Other.DbAdapter;
import com.radiant.acsl.myworkapp.Other.PopulateDb;
import com.radiant.acsl.myworkapp.Other.TallyDb;
import com.radiant.acsl.myworkapp.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 */
public class TallyEntryFramnt extends Fragment implements View.OnClickListener {

    private TableLayout tblLayout;
    private Button btnCheck;
    Spinner spinVoucher;
    private TextView txtDate;
    EditText edtNarrate;
    private Button btnSubmit;
    private FloatingActionButton fab;
    private ArrayAdapter<Ledger> ledgerArrayAdapter;
    private ArrayAdapter<CharSequence> adapter;
    private ArrayAdapter<CharSequence> adapterVch;
    private boolean isChecked;
    private int year, month, day;

    private Calendar calendar;
    private TallyDb dbTally;
    private PopulateDb populateDb;
    private SpinnerAdapter spinnerAdapter;

    public TallyEntryFramnt() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tally_entry_framnt, container, false);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        dbTally = TallyDb.getInstance(getActivity());
        ArrayList<Ledger> ledgers = DbAdapter.getInstance().getLedgers(dbTally);

        //ArrayAdapter --- Normal
        ledgerArrayAdapter = new ArrayAdapter<Ledger>(getActivity(), android.R.layout.simple_spinner_item, ledgers);
        ledgerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //ArrayAdapter --- Custom
        spinnerAdapter = new SpinnerAdapter(getActivity(), android.R.layout.simple_spinner_item, ledgers);

        //ArrayAdapter --- From Array Resource
//        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.ledgers, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        adapterVch = ArrayAdapter.createFromResource(getActivity(), R.array.Type, android.R.layout.simple_spinner_item);
        adapterVch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        tblLayout = (TableLayout) view.findViewById(R.id.tbl);
        isChecked = false;
        spinVoucher = (Spinner) view.findViewById(R.id.vchType);
        spinVoucher.setAdapter(adapterVch);
        spinVoucher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Log.i("Inside ", String.valueOf(tblLayout.getChildCount()));
                if (tblLayout.getChildCount() > 1 && adapterView.getItemAtPosition(position).toString().equals("Journal")) {
                    Log.i("Inside ", "1");
//                    tblLayout.removeViews(1, tblLayout.getChildCount() - 1);
                    for (int i = 1, j = tblLayout.getChildCount(); i < j; i++) {
                        View viewRow = tblLayout.getChildAt(i);

                        if (viewRow instanceof TableRow) {
                            TableRow row = (TableRow) viewRow;
                            Spinner spin = (Spinner) row.getChildAt(0);
                            Ledger ledger = (Ledger) spin.getSelectedItem();
                            if (ledger.getIsBankCash() == 1) {
                                tblLayout.removeView(row);
                            }
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        TallyHome tallyHome = (TallyHome) getActivity();
        tallyHome.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRow();
            }
        });
        calendar = Calendar.getInstance(TimeZone.getDefault());

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        edtNarrate = (EditText) view.findViewById(R.id.narration);
        txtDate = (TextView) view.findViewById(R.id.dateValue);
        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dateFragment = new DatePickerFragment();
                dateFragment.show(getFragmentManager(), "Date Picker");
            }
        });

        showdate(year, month + 1, day);
        btnSubmit = (Button) view.findViewById(R.id.submit);

//        txtDate.setText(String.valueOf(DateFormat.getDateInstance().format(new Date())));
        btnCheck = (Button) view.findViewById(R.id.btnCheck);
        btnSubmit.setEnabled(isChecked);
        btnSubmit.setOnClickListener(this);
        btnCheck.setOnClickListener(this);

        return view;


    }

    private void addRow() {
        isChecked = false;
        btnSubmit.setEnabled(isChecked);
        TableRow tf = (TableRow) getActivity().getLayoutInflater().inflate(R.layout.layout_tablerow, null);
        final Switch isCredit = (Switch) tf.getChildAt(1);
        final EditText edtRef = (EditText) tf.getChildAt(3);
        SearchableSpinner spinnerLedger = (SearchableSpinner) tf.getChildAt(0);
        spinnerLedger.setAdapter(ledgerArrayAdapter);
        spinnerLedger.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int poistion, long id) {
                Ledger ledger = (Ledger) adapterView.getSelectedItem();
//                Log.i("Item Selected", String.valueOf(ledger.getIsBillWise()));

                edtRef.setEnabled(ledger.getIsBillWise() == 1 ? true : false);
                if (spinVoucher.getSelectedItem().toString() == "Payment") {
                    if (ledger.getIsBankCash() == 1) {
                        isCredit.setChecked(true);
                        isCredit.setEnabled(false);
                    }
                } else if (spinVoucher.getSelectedItem().toString() == "Receipt") {
                    if (ledger.getIsBankCash() == 1) {
                        isCredit.setChecked(false);
                        isCredit.setEnabled(false);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        tblLayout.addView(tf);

    }

    private void showdate(int iYear, int iMonth, int iDay) {
        Log.i("date", String.valueOf(iMonth));
        txtDate.setText(new StringBuilder().append(java.lang.String.format("%02d", iDay)).append("-")
                .append(java.lang.String.format("%02d", iMonth))
                .append("-").append(iYear));
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
                        vchMain.setVoucherType(spinVoucher.getSelectedItem().toString());
                        vchMain.setNarration(edtNarrate.getText().toString());

                        for (int i = 1; i < tblLayout.getChildCount(); i++) {
                            TableRow tf1 = (TableRow) tblLayout.getChildAt(i);
                            Voucher voucher = new Voucher();
                            voucher.setLedgerName(((Spinner) tf1.getChildAt(0)).getSelectedItem().toString());
                            voucher.setCredit(((Switch) tf1.getChildAt(1)).isChecked());
                            voucher.setDblAmount(Double.parseDouble(((EditText) tf1.getChildAt(2)).getText().toString()));
                            voucher.setRef(((EditText) tf1.getChildAt(3)).getText().toString());
                            vch.add(voucher);
//                            Log.i("msg ", ((Spinner) tf1.getChildAt(0)).getSelectedItem().toString());
//                            Log.i("msg ", String.valueOf(((Switch) tf1.getChildAt(1)).isChecked()));
//                            Log.i("msg ", ((EditText) tf1.getChildAt(2)).getText().toString());
                        }
                        Log.i("Main Count", String.valueOf(vch.size()));
                        boolean iFlag = PopulateDb.getInstance().addVoucher(dbTally, vchMain, vch);
                        if (iFlag) {
                            if (tblLayout.getChildCount() > 1) {
                                tblLayout.removeViews(1, tblLayout.getChildCount() - 1);
                            }
                            edtNarrate.setText("");
                            getActivity().onBackPressed();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Pleae Add GLs", Toast.LENGTH_SHORT).show();
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
            case R.id.dateValue:
                getActivity().showDialog(999);
                break;
        }
    }

    protected Dialog onCreateDialog(int id) {
        if (id == 999) {
            return new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int iYear, int iMonth, int iDay) {
                    year = iYear;
                    month = iMonth;
                    day = iDay;
                    showdate(year, (month + 1), day);
                }
            }, year, month, day);
        }
        return null;
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
            Toast.makeText(getActivity(), "Credit Ledger not available.", Toast.LENGTH_SHORT).show();
        } else if (Double.compare(dblDebit, dblCredit) != 0) {
            Toast.makeText(getActivity(), "Dr/Cr Balance not matched.", Toast.LENGTH_SHORT).show();
        } else if (dblCredit <= 0 && dblDebit <= 0) {
            Toast.makeText(getActivity(), "Dr/Cr Balance should greater than 0. ", Toast.LENGTH_SHORT).show();
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
