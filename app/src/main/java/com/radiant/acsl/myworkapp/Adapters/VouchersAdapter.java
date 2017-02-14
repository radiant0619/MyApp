package com.radiant.acsl.myworkapp.Adapters;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.radiant.acsl.myworkapp.Modals.Ledger;
import com.radiant.acsl.myworkapp.Modals.Voucher;
import com.radiant.acsl.myworkapp.Modals.VoucherMain;
import com.radiant.acsl.myworkapp.R;

import java.util.ArrayList;

/**
 * Created by sakthivel on 23/01/2017.
 */

public class VouchersAdapter<T> extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<T> mList;
    SparseBooleanArray sparseBooleanArray;


    public VouchersAdapter(Context ctx, ArrayList<T> list) {

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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View getView(int position, View convertedView, ViewGroup viewGroup) {
//        Log.i("Loop Sl ", String.valueOf(position));
        if (convertedView == null) {
            convertedView = inflater.inflate(R.layout.list_multipletext, null);
        }
//        TextView txtId = (TextView) convertedView.findViewById(R.id.slId);
        //        TextView txtEsported = (TextView) convertedView.findViewById(R.id.isExport);

        TextView txtType = (TextView) convertedView.findViewById(R.id.Type);
        TextView txtDate = (TextView) convertedView.findViewById(R.id.PostDate);

        TextView txtNarrate = (TextView) convertedView.findViewById(R.id.narration);
        CheckBox chkBox = (CheckBox) convertedView.findViewById(R.id.chkSelect);
        boolean iFlag = false;
//        txtId.setVisibility(View.GONE);
        if (getItem(position) instanceof VoucherMain) {
            VoucherMain voucher = (VoucherMain) getItem(position);
//            txtId.setText(String.valueOf(voucher.getId()));
//            txtEsported.setText(String.valueOf(voucher.getisExported()).toUpperCase());
            txtType.setText(voucher.getVoucherType());
            txtType.setPadding(20,0,0,0);
            txtDate.setText(String.valueOf(voucher.getPostDate()));
            txtNarrate.setText(String.valueOf(voucher.getNarration()).toUpperCase());
            iFlag = voucher.getisExported();
            chkBox.setEnabled(!iFlag);
            chkBox.setTag(position);
            chkBox.setChecked(sparseBooleanArray.get(position));
            chkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    sparseBooleanArray.put((Integer) buttonView.getTag(), isChecked);
                }
            });
        }
        if (getItem(position) instanceof Ledger) {
            chkBox.setVisibility(View.GONE);
//            txtId.setVisibility(View.GONE);

            Ledger ledger = (Ledger) getItem(position);
//            txtId.setText(String.valueOf(ledger.getId()));
            txtType.setText(ledger.getAcctName());
            txtDate.setText(String.valueOf(ledger.getIsBankCash() == 0 ? false : true).toUpperCase());
            txtDate.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            txtDate.setTextSize(8);
            txtNarrate.setText(String.valueOf(ledger.getIsBillWise() == 0 ? false : true).toUpperCase());

            txtType.setWidth(460);
            txtType.setGravity(Gravity.LEFT);
        }

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
