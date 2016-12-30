        package com.radiant.acsl.myworkapp.Activity;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.radiant.acsl.myworkapp.R;

import java.util.ArrayList;
import java.util.List;

        public class AndroidProvider extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_provider);
        List<String> list= new ArrayList<String>();
        list.add("bluetooth_address - "+ android.provider.Settings.Secure.getString(getApplicationContext().getContentResolver(),"bluetooth_address"));
        list.add("ANDROID_ID - "+ Settings.Secure.ANDROID_ID);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_text, R.id.label, list);

        ((ListView) findViewById(R.id.listInfoProvider)).setAdapter(arrayAdapter);

    }
}
