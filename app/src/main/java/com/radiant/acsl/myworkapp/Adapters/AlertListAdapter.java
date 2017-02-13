package com.radiant.acsl.myworkapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.radiant.acsl.myworkapp.Modals.Voucher;
import com.radiant.acsl.myworkapp.R;

import java.util.ArrayList;

/**
 * Created by sakthivel on 13/02/2017.
 */


public class AlertListAdapter extends BaseAdapter {

    ArrayList<Voucher> mData;
    Context mContext;
    LayoutInflater inflater;

    public AlertListAdapter(ArrayList<Voucher> data, Context context) {
        mData = data;
        mContext = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_alert, null);
        }
        TextView tvTitle = (TextView) convertView.findViewById(R.id.name);
        TextView tvType = (TextView) convertView.findViewById(R.id.gender);
        TextView tvAmount = (TextView) convertView.findViewById(R.id.age);
        TextView tvRef = (TextView) convertView.findViewById(R.id.status);

        tvTitle.setText(mData.get(position).getLedgerName());
        tvType.setText(mData.get(position).getIsCredit() == true ? "Cr" : "Dr");
        tvAmount.setText(String.valueOf(mData.get(position).getDblAmount()));
        tvRef.setText(mData.get(position).getRef());

        return convertView;
    }
}

