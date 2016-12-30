package com.radiant.acsl.myworkapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.radiant.acsl.myworkapp.Fragments.SampleDialogFragment;
import com.radiant.acsl.myworkapp.R;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> listMan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//      D:\Android\sdk\tools\emulator.exe -netdelay none -netspeed full -avd Nexus_4_API_23 -dns-server 192.168.0.1
        listView = (ListView) findViewById(R.id.listManager);

        String[] workingsList = getResources().getStringArray(R.array.SamplePrj);
        listMan = new ArrayList<String>();
        listMan.addAll(Arrays.asList(workingsList));
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_text, listMan);

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long ID) {
                String value = listMan.get(position);
                switch (value.toString()) {
                    case "Android Build":
                        Intent intent = new Intent(MainActivity.this, DeviceInfo2.class);
                        startActivity(intent);
                        break;
                    case "Network Manager":
                        Intent intent1 = new Intent(MainActivity.this, NetworkManage.class);
                        startActivity(intent1);
                        break;
                    case "Telephone Manager":
                        Intent intent2 = new Intent(MainActivity.this, TelephoneManage.class);
                        startActivity(intent2);
                        break;
                    case "Wifi Manager":
                        Intent intent3 = new Intent(MainActivity.this, WifiManage.class);
                        startActivity(intent3);
                        break;
                    case "BlueTooth":
                        Intent intent4 = new Intent(MainActivity.this, BluetoothManage.class);
                        startActivity(intent4);
                        break;
                    case "Provider":
                        Intent intent5 = new Intent(MainActivity.this, AndroidProvider.class);
                        startActivity(intent5);
                        break;
                    case "PDF Viewer":
                        Intent intent6 = new Intent(MainActivity.this, PDFViewer.class);
                        startActivity(intent6);
                        break;
                    case "PDF Reader":
                        Intent intent7 = new Intent(MainActivity.this, PdfRead.class);
                        startActivity(intent7);
                        break;
                    case "Navigation Fragment":
                        Intent intent8 = new Intent(MainActivity.this, NavFragment.class);
                        startActivity(intent8);
                        break;
                    case "Tab Swipe Fragment":
                        Intent intent9 = new Intent(MainActivity.this, PDFViewer.class);
                        startActivity(intent9);
                        break;

                    case "Recycler View":
                        Intent intent10 = new Intent(MainActivity.this, RecyclerViewTest.class);
                        startActivity(intent10);
                        break;
                    case "Dialog":
                        Intent intent11 = new Intent(MainActivity.this, DialogSample.class);
                        startActivity(intent11);
                        break;

                    case "Matrix View":
                        Intent intent12 = new Intent(MainActivity.this, MatrixDisplay.class);
                        startActivity(intent12);
                        break;
                }
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_favorite:
                return true;
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu,menu);
//
//        return super.onCreateOptionsMenu(menu);
//    }
}
