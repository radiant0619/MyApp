package com.radiant.acsl.myworkapp.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by sakthivel on 22/11/2016.
 */
public class PDFViewer extends AppCompatActivity {
    WebView webView;

    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        //setContentView(R.layout.pdfviewer);
        //WebView webView=(WebView)findViewById(R.id.webviewpdf);
        webView = new WebView(PDFViewer.this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new MyWebClient());
        webView.canGoBack();
//        webView.getSettings().setPluginsEnabled(true);
//        http://foersom.com/net/HowTo/data/OoPdfFormExample.pdf
//        webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=http://foersom.com/net/HowTo/data/OoPdfFormExample.pdf");
//        webView.loadUrl("https://developer.android.com/reference/android/webkit/WebView.html");

        webView.loadUrl("http://45.118.182.50:8089/");
        setContentView(webView);
    }

    public class MyWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
