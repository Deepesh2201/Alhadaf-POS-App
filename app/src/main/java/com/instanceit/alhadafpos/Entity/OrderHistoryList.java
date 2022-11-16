package com.instanceit.alhadafpos.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OrderHistoryList {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("transactionid")
    @Expose
    private String transactionid;
    @SerializedName("orderno")
    @Expose
    private String orderno;
    @SerializedName("storeid")
    @Expose
    private String storeid;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("membername")
    @Expose
    private String membername;
    @SerializedName("membercontact")
    @Expose
    private String membercontact;
    @SerializedName("orderdate")
    @Expose
    private String orderdate;
    @SerializedName("totalamount")
    @Expose
    private String totalamount;
    @SerializedName("totaltaxableamt")
    @Expose
    private String totaltaxableamt;
    @SerializedName("totaltax")
    @Expose
    private String totaltax;
    @SerializedName("totalpaid")
    @Expose
    private String totalpaid;
    @SerializedName("totalpayableamt")
    @Expose
    private String totalpayableamt;
    @SerializedName("totalpaidamount")
    @Expose
    private String totaldiscount;
    @SerializedName("totaldiscount")
    @Expose
    private String totalpaidamount;
    @SerializedName("totalchangeamount")
    @Expose
    private String totalchangeamount;
    @SerializedName("ordernotes")
    @Expose
    private String ordernotes;
    @SerializedName("ofulldate")
    @Expose
    private String ofulldate;

    @SerializedName("entrypersonname")
    @Expose
    private String entrypersonname;

    @SerializedName("entrypersoncontact")
    @Expose
    private String entrypersoncontact;

    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("isstoreorderdetail")
    @Expose
    private String isstoreorderdetail;
    @SerializedName("storeorderdetailinfo")
    @Expose
    private ArrayList<Storeorderdetailinfo> storeorderdetailinfo = null;
    @SerializedName("isstoreorderpaymentdetail")
    @Expose
    private String isstoreorderpaymentdetail;
    @SerializedName("storeorderpaymentdetailinfo")
    @Expose
    private ArrayList<Storeorderpaymentdetailinfo> storeorderpaymentdetailinfo = null;

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

    public String getStoreid() {
        return storeid;
    }

    public void setStoreid(String storeid) {
        this.storeid = storeid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public String getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
    }

    public String getTotaltaxableamt() {
        return totaltaxableamt;
    }

    public void setTotaltaxableamt(String totaltaxableamt) {
        this.totaltaxableamt = totaltaxableamt;
    }

    public String getTotaldiscount() {
        return totaldiscount;
    }

    public void setTotaldiscount(String totaldiscount) {
        this.totaldiscount = totaldiscount;
    }

    public String getTotaltax() {
        return totaltax;
    }

    public void setTotaltax(String totaltax) {
        this.totaltax = totaltax;
    }

    public String getTotalpaid() {
        return totalpaid;
    }

    public void setTotalpaid(String totalpaid) {
        this.totalpaid = totalpaid;
    }

    public String getTotalpayableamt() {
        return totalpayableamt;
    }

    public void setTotalpayableamt(String totalpayableamt) {
        this.totalpayableamt = totalpayableamt;
    }

    public String getTotalpaidamount() {
        return totalpaidamount;
    }

    public void setTotalpaidamount(String totalpaidamount) {
        this.totalpaidamount = totalpaidamount;
    }

    public String getTotalchangeamount() {
        return totalchangeamount;
    }

    public void setTotalchangeamount(String totalchangeamount) {
        this.totalchangeamount = totalchangeamount;
    }

    public String getOrdernotes() {
        return ordernotes;
    }

    public void setOrdernotes(String ordernotes) {
        this.ordernotes = ordernotes;
    }

    public String getOfulldate() {
        return ofulldate;
    }

    public void setOfulldate(String ofulldate) {
        this.ofulldate = ofulldate;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getIsstoreorderdetail() {
        return isstoreorderdetail;
    }

    public void setIsstoreorderdetail(String isstoreorderdetail) {
        this.isstoreorderdetail = isstoreorderdetail;
    }

    public ArrayList<Storeorderdetailinfo> getStoreorderdetailinfo() {
        return storeorderdetailinfo;
    }

    public void setStoreorderdetailinfo(ArrayList<Storeorderdetailinfo> storeorderdetailinfo) {
        this.storeorderdetailinfo = storeorderdetailinfo;
    }

    public String getIsstoreorderpaymentdetail() {
        return isstoreorderpaymentdetail;
    }

    public void setIsstoreorderpaymentdetail(String isstoreorderpaymentdetail) {
        this.isstoreorderpaymentdetail = isstoreorderpaymentdetail;
    }

    public ArrayList<Storeorderpaymentdetailinfo> getStoreorderpaymentdetailinfo() {
        return storeorderpaymentdetailinfo;
    }

    public void setStoreorderpaymentdetailinfo(ArrayList<Storeorderpaymentdetailinfo> storeorderpaymentdetailinfo) {
        this.storeorderpaymentdetailinfo = storeorderpaymentdetailinfo;
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
}
