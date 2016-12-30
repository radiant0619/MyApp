package com.radiant.acsl.myworkapp.Activity;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.radiant.acsl.myworkapp.R;

import java.util.ArrayList;
import java.util.List;

public class DeviceInfo2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info2);
        Toolbar mytoolbar = (Toolbar)findViewById(R.id.actToolbar);
        setSupportActionBar(mytoolbar);

        List<String> list = new ArrayList<String>();
        list.add("BRAND - " + Build.BRAND);
        list.add("DEVICE - " + Build.DEVICE);
        list.add("DISPLAY - " + Build.DISPLAY);
        list.add("SERIAL - " + Build.SERIAL);
        list.add("PRODUCT - " + Build.PRODUCT);
        list.add("VERSION - " + Build.VERSION.RELEASE);
        list.add("ID - " + Build.ID);
        list.add("MANFUACTURER - " + Build.MANUFACTURER);
        list.add("BOARD - " + Build.BOARD);
        list.add("HARDWARE - " + Build.HARDWARE);
        list.add("HOST - " + Build.HOST);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.list_text,R.id.label,list);
        ((ListView) findViewById(R.id.listInfo)).setAdapter(arrayAdapter);


    }
}
