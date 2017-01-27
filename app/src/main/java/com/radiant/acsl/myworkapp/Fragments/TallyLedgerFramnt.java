package com.radiant.acsl.myworkapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.radiant.acsl.myworkapp.Adapters.VouchersAdapter;
import com.radiant.acsl.myworkapp.Modals.Ledger;
import com.radiant.acsl.myworkapp.Modals.VoucherMain;
import com.radiant.acsl.myworkapp.Other.DbAdapter;
import com.radiant.acsl.myworkapp.Other.PopulateDb;
import com.radiant.acsl.myworkapp.Other.TallyDb;
import com.radiant.acsl.myworkapp.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TallyLedgerFramnt extends Fragment {


    private EditText edtActName;
    private EditText edtShortName;
    private Switch aSwitch;
    private Switch bSwitch;
    private ListView listView;
    private Button btnSave;
    private Button btnCancel;
    private TallyDb tallyDb;

    private VouchersAdapter<Ledger> arrayAdapter1;
    private ArrayList<Ledger> ledgerList;
    private DbAdapter dbAdapter;

    public TallyLedgerFramnt() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tally_ledger_framnt, container, false);
        tallyDb = TallyDb.getInstance(getActivity());
        edtActName = (EditText) view.findViewById(R.id.acctName);
        edtShortName = (EditText) view.findViewById(R.id.acctShortName);
        aSwitch = (Switch) view.findViewById(R.id.isBankCash);
        bSwitch = (Switch) view.findViewById(R.id.isBillWise);
        btnSave = (Button) view.findViewById((R.id.btnSave));
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                boolean bFlag = false;

                if (edtActName.getText().toString() == null || edtActName.getText().toString() == "") {
                    edtActName.setError("Account Name Should not empty");
                    Toast.makeText(getContext(), "Account Name Should not empty", Toast.LENGTH_LONG);
                    return;
                }
//                if (bFlag) {
                Ledger ledger = new Ledger(0, edtActName.getText().toString(), edtShortName.getText().toString(), aSwitch.isChecked() ? 1 : 0, bSwitch.isChecked() ? 1 : 0);
                PopulateDb.getInstance().addLedger(tallyDb, ledger);
                ResetControls();
                LoadListView();
//                }
            }
        });

        btnCancel = (Button) view.findViewById((R.id.btnCancel));
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResetControls();
            }
        });

        listView = (ListView) view.findViewById((R.id.listLedger));
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {

                Ledger led = (Ledger) listView.getAdapter().getItem(position);

                edtActName.setText(led.getAcctName());
                edtShortName.setText(led.getAcctShort());
                aSwitch.setChecked(led.getIsBankCash() == 0 ? false : true);
                bSwitch.setChecked(led.getIsBillWise() == 0 ? false : true);

                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

            }
        });

        LoadListView();
        return view;
    }

    private void LoadListView() {

        ledgerList = dbAdapter.getInstance().getLedgers(tallyDb);
        arrayAdapter1 = new VouchersAdapter<Ledger>(getActivity(), ledgerList);
        listView.setAdapter(arrayAdapter1);
    }

    private void ResetControls() {
        edtActName.setText("");
        edtShortName.setText("");
        aSwitch.setChecked(false);
        bSwitch.setChecked(false);
    }

}
