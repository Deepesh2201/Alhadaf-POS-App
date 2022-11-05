package com.instanceit.alhadafpos.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReportModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("transactionid")
    @Expose
    private String transactionid;
    @SerializedName("orderno")
    @Expose
    private String orderno;
    @SerializedName("membername")
    @Expose
    private String membername;
    @SerializedName("membercontact")
    @Expose
    private String membercontact;
    @SerializedName("ofulldate")
    @Expose
    private String ofulldate;
    @SerializedName("totalamount")
    @Expose
    private String totalamount;
    @SerializedName("entrypersonname")
    @Expose
    private String entrypersonname;
    @SerializedName("entrypersoncontact")
    @Expose
    private String entrypersoncontact;
    @SerializedName("ordstatusname")
    @Expose
    private String ordstatusname;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("isitemdetail")
    @Expose
    private String isitemdetail;
    @SerializedName("paymenttypename")
    @Expose
    private String paymenttypename;
    @SerializedName("itemdetail")
    @Expose
    private ArrayList<Itemdetail> itemdetail = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getMembername() {
        return membername;
    }

    public void setMembername(String membername) {
        this.membername = membername;
    }

    public String getMembercontact() {
        return membercontact;
    }

    public void setMembercontact(String membercontact) {
        this.membercontact = membercontact;
    }

    public String getOfulldate() {
        return ofulldate;
    }

    public void setOfulldate(String ofulldate) {
        this.ofulldate = ofulldate;
    }

    public String getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
    }

    public String getEntrypersonname() {
        return entrypersonname;
    }

    public void setEntrypersonname(String entrypersonname) {
        this.entrypersonname = entrypersonname;
    }

    public String getEntrypersoncontact() {
        return entrypersoncontact;
    }

    public void setEntrypersoncontact(String entrypersoncontact) {
        this.entrypersoncontact = entrypersoncontact;
    }

    public String getOrdstatusname() {
        return ordstatusname;
    }

    public void setOrdstatusname(String ordstatusname) {
        this.ordstatusname = ordstatusname;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getIsitemdetail() {
        return isitemdetail;
    }

    public void setIsitemdetail(String isitemdetail) {
        this.isitemdetail = isitemdetail;
    }

    public ArrayList<Itemdetail> getItemdetail() {
        return itemdetail;
    }

    public void setItemdetail(ArrayList<Itemdetail> itemdetail) {
        this.itemdetail = itemdetail;
    }

    public String getPaymenttypename() {
        return paymenttypename;
    }

    public void setPaymenttypename(String paymenttypename) {
        this.paymenttypename = paymenttypename;
    }
}
