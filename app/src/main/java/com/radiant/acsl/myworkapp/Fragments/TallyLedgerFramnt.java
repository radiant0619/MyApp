package com.radiant.acsl.myworkapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.radiant.acsl.myworkapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TallyLedgerFramnt extends Fragment {


    public TallyLedgerFramnt() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tally_ledger_framnt, container, false);
    }

}
