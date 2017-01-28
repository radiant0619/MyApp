package com.radiant.acsl.myworkapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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

import static com.radiant.acsl.myworkapp.Other.TallyDb.FLD_ID;
import static com.radiant.acsl.myworkapp.Other.TallyDb.TBL_LEDGER;

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
    private Button btnDelete;
    private TallyDb tallyDb;
    private Ledger ledgerEdit;

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
        ledgerEdit = null;
        edtActName = (EditText) view.findViewById(R.id.acctName);
        edtShortName = (EditText) view.findViewById(R.id.acctShortName);
        aSwitch = (Switch) view.findViewById(R.id.isBankCash);
        bSwitch = (Switch) view.findViewById(R.id.isBillWise);
        btnSave = (Button) view.findViewById((R.id.btnSave));
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                boolean bFlag = false;

                if (edtActName.getText().toString().length() == 0) {
                    edtActName.setError("Account Name cannot be blank");
//                    Toast.makeText(getContext(), "Account Name Should not empty", Toast.LENGTH_LONG);
                    return;
                }
                if (ledgerEdit == null) {
//                if (bFlag) {
                    Ledger ledger = new Ledger(0, edtActName.getText().toString(), edtShortName.getText().toString(), aSwitch.isChecked() ? 1 : 0, bSwitch.isChecked() ? 1 : 0);
                    PopulateDb.getInstance().addLedger(tallyDb, ledger);
                } else {
                    ledgerEdit.setAcctName(edtActName.getText().toString());
                    ledgerEdit.setAcctShort(edtShortName.getText().toString());
                    ledgerEdit.setIsBankCash(aSwitch.isChecked() ? 1 : 0);
                    ledgerEdit.setIsBillWise(bSwitch.isChecked() ? 1 : 0);
                    PopulateDb.getInstance().updateLedger(tallyDb, ledgerEdit);
                }
                ResetControls();


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

        btnDelete = (Button) view.findViewById((R.id.btnDelete));
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopulateDb.getInstance().Delete(tallyDb, TBL_LEDGER, FLD_ID, String.valueOf(ledgerEdit.getId()));
                ResetControls();

            }
        });
        listView = (ListView) view.findViewById((R.id.listLedger));
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {

                ledgerEdit = (Ledger) listView.getAdapter().getItem(position);
                Log.i("Long Press ", "Called");
                edtActName.setText(ledgerEdit.getAcctName());
                edtShortName.setText(ledgerEdit.getAcctShort());
                aSwitch.setChecked(ledgerEdit.getIsBankCash() == 0 ? false : true);
                bSwitch.setChecked(ledgerEdit.getIsBillWise() == 0 ? false : true);
                btnDelete.setEnabled(true);
                return true;
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
        ledgerEdit = null;
        btnDelete.setEnabled(false);
        edtActName.setText("");
        edtShortName.setText("");
        aSwitch.setChecked(false);
        bSwitch.setChecked(false);
        LoadListView();
    }

}
