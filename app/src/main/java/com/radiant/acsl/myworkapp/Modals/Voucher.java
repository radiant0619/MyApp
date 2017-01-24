package com.radiant.acsl.myworkapp.Modals;

import android.telephony.SignalStrength;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sakthivel on 09/01/2017.
 */
public class Voucher {
    private int Id;
    private String ledgerName;
    private boolean isCredit;
    private String ref;
    private double dblAmount;


    public Voucher(JSONObject object) {
        try {
            this.Id = Integer.parseInt(object.getString("Id"));
            this.ledgerName = object.getString("name");
            this.isCredit = Boolean.parseBoolean(object.getString("iscredit"));
            this.ref = object.getString("ref");
            this.dblAmount = Double.parseDouble(object.getString("dblamount"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Voucher() {

    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getLedgerName() {
        return ledgerName;
    }

    public void setLedgerName(String ledgerName) {
        this.ledgerName = ledgerName;
    }

    public boolean getIsCredit() {
        return isCredit;
    }

    public void setCredit(boolean credit) {
        isCredit = credit;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public double getDblAmount() {
        return dblAmount;
    }

    public void setDblAmount(double dblAmount) {
        this.dblAmount = dblAmount;
    }
}
