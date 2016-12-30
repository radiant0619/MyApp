package com.radiant.acsl.myworkapp.Activity;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.radiant.acsl.myworkapp.R;

import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WifiManage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_manage);
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String vMac = wifiInfo.getMacAddress();


        List<String> list = new ArrayList<String>();
        list.add("Wifi - getIpAddress - " + wifiInfo.getIpAddress());
        list.add("Wifi - getMacAddress - " + wifiInfo.getMacAddress());
        list.add("Wifi - getBSSID - " + wifiInfo.getBSSID());
        list.add("Wifi - getSSID - " + wifiInfo.getSSID());
        list.add("Wifi - getNetworkId - " + wifiInfo.getNetworkId());
        list.add("Wifi - getRssi - " + wifiInfo.getRssi());
        list.add("Wifi - getSupplicantState - " + wifiInfo.getSupplicantState());

        list.add("Network Interface - Mac Address - " + getMacAddre());

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_text, R.id.label, list);

        ((ListView) findViewById(R.id.listInfoWifi)).setAdapter(arrayAdapter);
    }

    public String getMacAddre() {
        String res1 = null;
        try {
            List<NetworkInterface> netList = Collections.list(NetworkInterface.getNetworkInterfaces());

            for (NetworkInterface nif : netList) {
                Log.v("NIF Name",nif.getName()  ) ;
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();

                if (macBytes == null) {
                    return "";
                }
                StringBuilder str = new StringBuilder();

                for (byte n : macBytes) {
                    str.append(String.format("%02X:",n));                }

                if (str.length() > 0) {
                    str.deleteCharAt(str.length() - 1);
                }
                res1 = str.toString();
                Log.v("Nif -"+nif.getName(),str.toString() );
                return res1;
            }
        } catch (Exception ex) {

        }
        return res1 = "02:00:00:00:00:00";
    }
}
