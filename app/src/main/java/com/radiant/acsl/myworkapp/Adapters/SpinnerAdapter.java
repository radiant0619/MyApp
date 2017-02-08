package com.radiant.acsl.myworkapp.Adapters;

import android.app.Activity;

import com.radiant.acsl.myworkapp.Modals.Ledger;
import com.radiant.acsl.myworkapp.R;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by rsvra on 29/01/2017.
 */

public class SpinnerAdapter extends ArrayAdapter<Ledger> {

    private Activity context;
    ArrayList<Ledger> data = null;

    public SpinnerAdapter(Activity context, int resource, ArrayList<Ledger> data) {
        super(context, resource, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {   // Ordinary view in Spinner, we use android.R.layout.simple_spinner_item
        return super.getView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {   // This view starts when we click the spinner.
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            row = inflater.inflate(R.layout.spinner_layout, parent, false);
        }

        Ledger item = data.get(position);

        if (item != null) {   // Parse the data from each object and set it.

            TextView acctNmae = (TextView) row.findViewById(R.id.acctName);

            if (acctNmae != null)
                acctNmae.setText(item.getAcctName());

        }

        return row;
    }

}
