package com.radiant.acsl.myworkapp.Modals;

import java.util.Date;

/**
 * Created by sakthivel on 10/01/2017.
 */

public class VoucherMain {
    private int Id;
    private String voucherType;
    private String postDate;
    private String narration;
    private boolean isExported;

    public VoucherMain() {
    }

    public VoucherMain(int id, String voucherType, String postDate,  boolean isExported) {
        Id = id;
        this.voucherType = voucherType;
        this.postDate = postDate;
        this.isExported = isExported;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }
    public boolean getisExported() {
        return isExported;
    }

    public void setIsExported(boolean exported) {
        isExported = exported;
    }
}
