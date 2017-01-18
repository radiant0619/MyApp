package com.radiant.acsl.myworkapp.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.radiant.acsl.myworkapp.Modals.Voucher;
import com.radiant.acsl.myworkapp.Modals.VoucherMain;
import com.radiant.acsl.myworkapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sakthivel on 11/01/2017.
 */

public class VoucherListAdapter extends ArrayAdapter<VoucherMain> {


    public VoucherListAdapter(Context mContext, ArrayList<VoucherMain> data) {
        super(mContext, 0, data);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertedView, ViewGroup parent) {

        VoucherMain voucher = getItem(position);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        if (convertedView == null) {
            convertedView = inflater.inflate(R.layout.list_multipletext, null);
        }
//        convertedView = inflater.inflate(R.layout.list_multipletext, parent);

        CheckBox chkBodx = (CheckBox) convertedView.findViewById(R.id.chkSelect);
        TextView txtId = (TextView) convertedView.findViewById(R.id.slId);
        TextView txtType = (TextView) convertedView.findViewById(R.id.Type);
        TextView txtDate = (TextView) convertedView.findViewById(R.id.PostDate);
        TextView txtEsported = (TextView) convertedView.findViewById(R.id.isExport);


        txtId.setText(String.valueOf(voucher.getId()));
        txtType.setText(voucher.getVoucherType());
        txtDate.setText(String.valueOf(voucher.getPostDate()));
        txtEsported.setText(String.valueOf(voucher.getisExported()));
//        Log.i("Value in List", voucher.getLedgerName());
        return convertedView;

    }
}
