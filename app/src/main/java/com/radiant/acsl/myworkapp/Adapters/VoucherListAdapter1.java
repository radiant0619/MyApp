package com.radiant.acsl.myworkapp.Adapters;

import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.radiant.acsl.myworkapp.Modals.Voucher;
import com.radiant.acsl.myworkapp.Modals.VoucherMain;
import com.radiant.acsl.myworkapp.R;

import java.util.ArrayList;

/**
 * Created by sakthivel on 23/01/2017.
 */

public class VoucherListAdapter1<T> extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<T> mList;
    SparseBooleanArray sparseBooleanArray;


    public VoucherListAdapter1(Context ctx, ArrayList<T> list) {

        this.context = ctx;
        mList = new ArrayList<T>();
        sparseBooleanArray = new SparseBooleanArray();
        this.mList = list;
        this.inflater = LayoutInflater.from(this.context);

    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertedView, ViewGroup viewGroup) {

        if (convertedView == null) {
//            Log.i("Is convertedView null","Yes");
            convertedView = inflater.inflate(R.layout.list_multipletext, null);
        }


        TextView txtId = (TextView) convertedView.findViewById(R.id.slId);
        TextView txtType = (TextView) convertedView.findViewById(R.id.Type);
        TextView txtDate = (TextView) convertedView.findViewById(R.id.PostDate);
        TextView txtEsported = (TextView) convertedView.findViewById(R.id.isExport);
        TextView txtNarrate = (TextView) convertedView.findViewById(R.id.narration);
        boolean iFlag = false;
        if (getItem(position) instanceof VoucherMain) {
//            Log.i("Is Voucher Main","Yes");
            VoucherMain voucher = (VoucherMain) getItem(position);
            txtId.setText(String.valueOf(voucher.getId()));
            txtType.setText(voucher.getVoucherType());
            txtDate.setText(String.valueOf(voucher.getPostDate()));
            txtEsported.setText(String.valueOf(voucher.getisExported()).toUpperCase());
            txtNarrate.setText(String.valueOf(voucher.getNarration()).toUpperCase());
            iFlag = voucher.getisExported();
        }

        CheckBox chkBox = (CheckBox) convertedView.findViewById(R.id.chkSelect);
        chkBox.setEnabled(!iFlag);
        chkBox.setTag(position);
        chkBox.setChecked(sparseBooleanArray.get(position));
        chkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sparseBooleanArray.put((Integer) buttonView.getTag(), isChecked);
            }
        });

        return convertedView;
    }

    public ArrayList<T> getCheckedItems() {
        ArrayList<T> arrayList = new ArrayList<T>();
        for (int i = 0; i <= mList.size(); i++) {
            if (sparseBooleanArray.get(i)) {
                arrayList.add(mList.get(i));
            }
        }
        return arrayList;
    }

}
