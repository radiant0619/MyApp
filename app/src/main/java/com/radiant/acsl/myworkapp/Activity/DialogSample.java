package com.radiant.acsl.myworkapp.Activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.radiant.acsl.myworkapp.Fragments.SampleDialogFragment;
import com.radiant.acsl.myworkapp.R;

public class DialogSample extends AppCompatActivity implements View.OnClickListener {

    private TextView dlgNormal;
    private TextView dlgList;
    private TextView dlgOption;
    private TextView dlgothers;
    private TextView valueFld;
    private AlertDialog dlg;
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_sample);
        builder = new AlertDialog.Builder(this);
        dlgList = (TextView) findViewById(R.id.dlgList);
        dlgOption = (TextView) findViewById(R.id.dlgOption);
        dlgNormal = (TextView) findViewById(R.id.dlgNormal);
        dlgothers = (TextView) findViewById(R.id.dlgOthers);
        valueFld = (TextView) findViewById(R.id.valueFld);

        dlgList.setOnClickListener(this);
        dlgOption.setOnClickListener(this);
        dlgNormal.setOnClickListener(this);
        dlgothers.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
//  AlertDialog dlg;//= new Dialog(new ContextThemeWrapper(this,R.style.DialogSlideAnim));
        builder.setCancelable(true);

        switch (view.getId()) {
            case (R.id.dlgList):
                if (dlg != null) {
                    if (dlg.isShowing()) {
                        dlg.hide();
                        return;
                    }
                }
                builder.setTitle("Select any age");
//                builder.setMessage("Test");
                builder.setItems(R.array.age, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        Toast.makeText(DialogSample.this, getResources().getTextArray(R.array.age)[i], Toast.LENGTH_SHORT).show();
                        valueFld.setText(getResources().getTextArray(R.array.age)[i]);
                    }
                });

                dlg = builder.create();
                dlg.getWindow().getAttributes().windowAnimations = R.style.Dlg_slide_right;
//                dlg.

                dlg.show();
                break;

            case (R.id.dlgNormal):
                builder.setTitle("Sample Title");
                builder.setIcon(R.drawable.placeholder);
                builder.setMessage("Test Alert Dialog");
                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "This is Normal Dialog", Toast.LENGTH_SHORT).show();
                    }
                });
                dlg = builder.create();
                dlg.getWindow().getAttributes().windowAnimations = R.style.dlg_fade;
                dlg.show();
                break;

            case (R.id.dlgOption):
                builder.setTitle("Sample Title");
                builder.setIcon(R.drawable.placeholder);
                builder.setMessage("Option");
                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "This is Normal Dialog", Toast.LENGTH_SHORT).show();
                    }
                });
                dlg = builder.create();
                dlg.getWindow().getAttributes().windowAnimations = R.style.Dlg_slide_up;
                dlg.show();
                break;

            case (R.id.dlgOthers):
                SampleDialogFragment dlg1 = new SampleDialogFragment();
                dlg1.show(getSupportFragmentManager(), "Dialog");
                break;
        }


    }


}
