package com.radiant.acsl.myworkapp.Other;

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

/**
 * Created by sakthivel on 17/01/2017.
 */

public class Constants {
    public final static String NO = "NO";
    public final static String YES = "YES";

    public static String encode(String strText) {
        byte[] data = new byte[0];
        String encodedString = null;

        try {
            data = strText.getBytes("UTF-8");
            encodedString = Base64.encodeToString(data, Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodedString;
    }

    public static String decode(String strText) {
        byte[] data = Base64.decode(strText, Base64.DEFAULT);
        String decodedStr = null;

        try {
            decodedStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return decodedStr;

    }
//    private void mailSend() {
//        Log.i("Mail","called");
//        new AsyncTask<String, Integer, Void>() {
//
//            @Override
//            protected Void doInBackground(String... strings) {
//                MailSender m = new MailSender("rsv.radiant@gmail.com", "gzixueiaajdeeunz");
//                Log.i("Mail","inprocess");
//                String[] toArr = {"rsv.radiant0619@gmail.com", "rs.lotus1991@gmail.com"};
//                m.set_to(toArr);
//                m.set_from("rsv.radiant@gmail.com");
//                m.set_subject("This is an email sent using my Mail JavaMail wrapper from an Android device.");
//                m.set_body("Email body.");
//
//                try {
//                    // m.addAttachment("/sdcard/filelocation");
//
//                    if (m.send()) {
//                        Toast.makeText(getActivity(), "Email was sent successfully.", Toast.LENGTH_LONG).show();
//                        Log.i("Mail","Ok");
//                    } else {
//                        Toast.makeText(getActivity(), "Email was not sent.", Toast.LENGTH_LONG).show();
//                        Log.i("Mail","Cancel");
//                    }
//                } catch (Exception e) {
//                    //Toast.makeText(MailApp.this, "There was a problem sending the email.", Toast.LENGTH_LONG).show();
//                    Log.e("MailApp", "Could not send email", e);
//                    e.printStackTrace();
//
//                }
//                return null;
//            }
//        }.execute();
//
//
//    }
}

