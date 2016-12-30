package com.radiant.acsl.myworkapp.Activity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Visibility;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.radiant.acsl.myworkapp.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Set;

public class PdfRead extends AppCompatActivity {
    WebView myWebView;
    LinearLayout linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        LinearLayout outerLinear = new LinearLayout(this);
//        outerLinear.setOrientation(LinearLayout.VERTICAL);
//
//        TextView tst = new TextView(outerLinear.getContext());
//        tst.setText("Hi");
//        outerLinear.addView(tst);
//
//        setContentView(outerLinear);

        setContentView(R.layout.activity_pdf_reader);
        myWebView = (WebView) findViewById(R.id.web);

        linear = (LinearLayout) findViewById(R.id.linear);
        AssetManager assetManager = getAssets();
//          Get Fillable PDF forms Fields & Values
        try {

            PdfReader pdfReader = new PdfReader(getResources().openRawResource(R.raw.tnform_aft));
            myWebView.setVisibility(View.INVISIBLE);

            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            AcroFields acroFields = pdfReader.getAcroFields();

//            public const int FIELD_TYPE_NONE = 0;
//            public const int FIELD_TYPE_PUSHBUTTON = 1;
//            public const int FIELD_TYPE_CHECKBOX = 2;
//            public const int FIELD_TYPE_RADIOBUTTON = 3;
//            public const int FIELD_TYPE_TEXT = 4;
//            public const int FIELD_TYPE_LIST = 5;
//            public const int FIELD_TYPE_COMBO = 6;
//            public const int FIELD_TYPE_SIGNATURE = 7;

//            for (Map<String, AcroFields.Item> field :
//                    acroFields.getFields()) {
//
//
//            }


            Set<String> fldNames = acroFields.getFields().keySet();
            for (String fldName : fldNames) {

                Log.i("Fields", fldName + " : " + acroFields.getField(fldName) + " : " + acroFields.getFieldType(fldName));
                Log.i("Fields Type", fldName + " : " + acroFields.getField(fldName) + " : " + acroFields.getFieldType(fldName));
                if (acroFields.getFieldType(fldName) == 6) {
                    LinearLayout lin = new LinearLayout(this);
                    lin.setOrientation(LinearLayout.HORIZONTAL);

                    TextView textView = new TextView(this);
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(fldName);
                    lin.addView(textView);

                    Spinner spinner = new Spinner(this);
                    spinner.setVisibility(View.VISIBLE);
                    String[] option = acroFields.getListOptionDisplay(fldName);
                    spinner.setAdapter(new ArrayAdapter<String>(this, R.layout.list_text, option));

                    lin.addView(spinner);
                    linear.addView(lin);

//                    for (String str : option){
//                        Log.i("Option List",str);
//
//                    }
                } else if (acroFields.getFieldType(fldName) == 4) {
                    LinearLayout lin = new LinearLayout(this);
                    lin.setOrientation(LinearLayout.HORIZONTAL);

                    TextView textView = new TextView(this);
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(fldName);
                    textView.setWidth(300);
                    lin.addView(textView);

                    EditText editView = new EditText(this);
                    editView.setVisibility(View.VISIBLE);
                    editView.setWidth(300);
                    editView.setText(acroFields.getField(fldName));
                    lin.addView(editView);
                    linear.addView(lin);

                } else if (acroFields.getFieldType(fldName) == 2) {
                    LinearLayout lin = new LinearLayout(this);
                    lin.setOrientation(LinearLayout.HORIZONTAL);

                    TextView textView = new TextView(this);
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(fldName);
                    lin.addView(textView);

                    CheckBox checkBox = new CheckBox(this);
                    checkBox.setVisibility(View.VISIBLE);

                    lin.addView(checkBox);
                    linear.addView(lin);

                }
//                else if (fldName.contains("List Box")) {
//
//                    TextView textView = new TextView(this);
//                    activity_pdf_reader.addView(textView);
//                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

////      Using Pdf.Js
//        File toPath = new File(getFilesDir(), "pdf");
//        Log.i("path", getFilesDir().toString());
//
//        copyAssetFolder(getAssets(), "pdf", toPath.getPath());
//
//        File indexPage = new File(toPath, "index.html");
//        myWebView = (WebView) findViewById(R.id.web);
//        myWebView.setPadding(0, 0, 0, 0);
//        myWebView.setInitialScale(1);
//
//        WebSettings webSettings = myWebView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//
//        myWebView.getSettings().setLoadWithOverviewMode(true);
//        myWebView.getSettings().setUseWideViewPort(true);
//        myWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
//        myWebView.setScrollbarFadingEnabled(false);
//        myWebView.setWebChromeClient(new WebChromeClient());
//        myWebView.setWebViewClient(new WebViewClient() {
//            private boolean doIt = false;
//
//            @Override
//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                doIt = true;
//                super.onPageStarted(view, url, favicon);
//            }
//
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                if (doIt) {
//                    loadPdf(R.raw.form);
//                    doIt = false;
//                }
//            }
//        });
//        myWebView.loadUrl("file:///" + indexPage.getAbsolutePath());

    }

    private void loadPdf(int rawResource) {
        String data = "data:application/pdf;base64," + base64Encode(getResources().openRawResource(rawResource));
        //String data="data:application/pdf;base64,JVBERi0xLjcKCjEgMCBvYmogICUgZW50cnkgcG9pbnQKPDwKICAvVHlwZSAvQ2F0YWxvZwogIC9QYWdlcyAyIDAgUgo+PgplbmRvYmoKCjIgMCBvYmoKPDwKICAvVHlwZSAvUGFnZXMKICAvTWVkaWFCb3ggWyAwIDAgMjAwIDIwMCBdCiAgL0NvdW50IDEKICAvS2lkcyBbIDMgMCBSIF0KPj4KZW5kb2JqCgozIDAgb2JqCjw8CiAgL1R5cGUgL1BhZ2UKICAvUGFyZW50IDIgMCBSCiAgL1Jlc291cmNlcyA8PAogICAgL0ZvbnQgPDwKICAgICAgL0YxIDQgMCBSIAogICAgPj4KICA+PgogIC9Db250ZW50cyA1IDAgUgo+PgplbmRvYmoKCjQgMCBvYmoKPDwKICAvVHlwZSAvRm9udAogIC9TdWJ0eXBlIC9UeXBlMQogIC9CYXNlRm9udCAvVGltZXMtUm9tYW4KPj4KZW5kb2JqCgo1IDAgb2JqICAlIHBhZ2UgY29udGVudAo8PAogIC9MZW5ndGggNDQKPj4Kc3RyZWFtCkJUCjcwIDUwIFRECi9GMSAxMiBUZgooSGVsbG8sIHdvcmxkISkgVGoKRVQKZW5kc3RyZWFtCmVuZG9iagoKeHJlZgowIDYKMDAwMDAwMDAwMCA2NTUzNSBmIAowMDAwMDAwMDEwIDAwMDAwIG4gCjAwMDAwMDAwNzkgMDAwMDAgbiAKMDAwMDAwMDE3MyAwMDAwMCBuIAowMDAwMDAwMzAxIDAwMDAwIG4gCjAwMDAwMDAzODAgMDAwMDAgbiAKdHJhaWxlcgo8PAogIC9TaXplIDYKICAvUm9vdCAxIDAgUgo+PgpzdGFydHhyZWYKNDkyCiUlRU9G";
        String javascript = "javascript:(function() { loadPDF('" + data + "'); })()";
        myWebView.loadUrl(javascript);
    }

    private static String base64Encode(InputStream is) {
        String encStr = "";
        try {
            int bytesRead = 0;
            int chunkSize = 10000000;
            byte[] chunk = new byte[chunkSize];
            while ((bytesRead = is.read(chunk)) > 0) {
                byte[] ba = new byte[bytesRead];

                for (int i = 0; i < ba.length; i++) {
                    ba[i] = chunk[i];
                }
                encStr += Base64.encodeToString(ba, Base64.DEFAULT | Base64.NO_PADDING | Base64.NO_WRAP);
            }
        } catch (IOException ex) {
            Log.w("PDF", ex.getMessage(), ex);
        }


        return encStr;
    }

    private static boolean copyAssetFolder(AssetManager asst, String fromAssetPath, String toPath) {
        boolean res = true;
        try {
            String[] files = asst.list(fromAssetPath);
            new File(toPath).mkdirs();

            for (String file : files) {
                Log.i("Path File", file);
                if (file.contains(".")) {
                    res &= copyAsset(asst,
                            fromAssetPath + "/" + file,
                            toPath + "/" + file);
                } else {
                    res &= copyAssetFolder(asst,
                            fromAssetPath + "/" + file,
                            toPath + "/" + file);
                }
            }
            return res;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean copyAsset(AssetManager assetManager, String fromAssetPath, String toPath) {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(fromAssetPath);
            Log.i("To Path", toPath);
            new File(toPath).createNewFile();
            out = new FileOutputStream(toPath);
            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Exception ", e.toString());
            return false;
        }
    }

    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }
}
