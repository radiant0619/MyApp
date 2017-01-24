package com.radiant.acsl.myworkapp.Activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.radiant.acsl.myworkapp.R;

import java.util.ArrayList;
import java.util.List;

public class NetworkManage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_manage);
        List<String> list = new ArrayList<String>();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1)
             ;
        } else {
            ConnectivityManager netManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = netManager.getActiveNetworkInfo();

            list.add("getActiveNetworkInfo - " + netManager.getActiveNetworkInfo());
            if (networkInfo != null) {
                list.add("getNetworkInfo - " + networkInfo.getTypeName());
                list.add("getNetworkOperator - " + networkInfo.getType());
            }
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_text, R.id.label, list);
        ((ListView) findViewById(R.id.listInfo)).setAdapter(arrayAdapter);
    }
}
