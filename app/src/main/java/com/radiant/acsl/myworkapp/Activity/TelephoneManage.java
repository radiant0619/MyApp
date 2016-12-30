package com.radiant.acsl.myworkapp.Activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.radiant.acsl.myworkapp.R;

import java.util.ArrayList;
import java.util.List;

public class TelephoneManage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telephone_manage);

        List<String> list = new ArrayList<String>();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_PHONE_STATE}, 1);
        } else {
//
//            SubscriptionManager subscriptionManager =(SubscriptionManager)getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
//            //subscriptionManager.getActiveSubscriptionInfoForSimSlotIndex(1);
//            SmsManager smsManager = SmsManager.getDefault();


            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);


            list.add("Device ID - " + telephonyManager.getDeviceId());
            list.add("Sim State - " + telephonyManager.getSimState());
            list.add("Sim Serial No - " + telephonyManager.getSimSerialNumber());
            list.add("Line1 Number - " + telephonyManager.getLine1Number());
            list.add("Network Type - " + telephonyManager.getNetworkType());
            list.add("SW Version - " + telephonyManager.getDeviceSoftwareVersion());
            list.add("Subscriber ID - " + telephonyManager.getSubscriberId());
            list.add("Operator Name - " + telephonyManager.getSimOperatorName());
            list.add("Phone Type ID - " + telephonyManager.getPhoneType());
            list.add("Cell State - " + telephonyManager.getCallState());
            list.add("Cell Locaton - " + telephonyManager.getCellLocation());


        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_text, R.id.label, list);
        ((ListView) findViewById(R.id.listTeleInfo)).setAdapter(arrayAdapter);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void yourMethod() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            getActionBar().setHomeButtonEnabled(false);
        }
    }
}
