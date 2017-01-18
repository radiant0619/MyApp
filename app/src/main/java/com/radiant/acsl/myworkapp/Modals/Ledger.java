package com.radiant.acsl.myworkapp.Modals;

/**
 * Created by sakthivel on 10/01/2017.
 */

public class Ledger {
    private int Id;
    private String acctName;
    private String acctShort;
    private int isBankCash;

    public int getIsBankCash() {
        return isBankCash;
    }

    public void setIsBankCash(int isBankCash) {
        this.isBankCash = isBankCash;
    }

    public int getIsBillWise() {
        return isBillWise;
    }

    public void setIsBillWise(int isBillWise) {
        this.isBillWise = isBillWise;
    }

    private int isBillWise;

    public Ledger() {
    }

    public Ledger(int id, String acctName, String acctShort, int isBankCash, int isBillWise) {
        Id = id;
        this.acctName = acctName;
        this.acctShort = acctShort;
        this.isBankCash = isBankCash;
        this.isBillWise = isBillWise;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    public String getAcctShort() {
        return acctShort;
    }

    public void setAcctShort(String acctShort) {
        this.acctShort = acctShort;
    }

    public int isBankCash() {
        return isBankCash;
    }


    public int isBillWise() {
        return isBillWise;
    }

    public void setBillWise(int billWise) {
        isBillWise = billWise;
    }
}
