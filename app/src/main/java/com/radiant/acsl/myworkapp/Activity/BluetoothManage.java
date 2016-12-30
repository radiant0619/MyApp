package com.radiant.acsl.myworkapp.Activity;

import android.bluetooth.BluetoothAdapter;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.radiant.acsl.myworkapp.R;

import java.util.ArrayList;
import java.util.List;

public class BluetoothManage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_manage);

        //BluetoothManage bluetoothManage = BluetoothManage().
        BluetoothAdapter blAdpter   = BluetoothAdapter.getDefaultAdapter();


        List<String> list = new ArrayList<String>();
        if (blAdpter!=null) {
            list.add("BlueTooth - getAddress - " + blAdpter.getAddress());
            list.add("BlueTooth - getName - " + blAdpter.getName());
            list.add("BlueTooth - getState - " + blAdpter.getState());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                list.add("BlueTooth - getBluetoothLeScanner - " + blAdpter.getBluetoothLeScanner());
            }
//            list.add("BlueTooth - getNetworkId - " + blAdpter.());
//            list.add("BlueTooth - getRssi - " + blAdpter.getRssi());
//            list.add("BlueTooth - getSupplicantState - " + blAdpter.getSupplicantState());
        }
        else
        {
            list.add("Device does not support bluetooth." );
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.list_text,R.id.label,list);

        ((ListView) findViewById(R.id.listInfoBlue)).setAdapter(arrayAdapter);
    }
}
