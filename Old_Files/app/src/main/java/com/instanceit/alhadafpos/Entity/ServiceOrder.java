package com.instanceit.alhadafpos.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ServiceOrder {
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
    @SerializedName("showeditbutton")
    @Expose
    private String showeditbutton;
    @SerializedName("showcancelbutton")
    @Expose
    private String showcancelbutton;
    @SerializedName("orderstatus")
    @Expose
    private String orderstatus;
    @SerializedName("orderstatuscolor")
    @Expose
    private String orderstatuscolor;
    @SerializedName("invoicepdfurl")
    @Expose
    private String invoicepdfurl;
    @SerializedName("isserviceorderdetail")
    @Expose
    private Object isserviceorderdetail;
    @SerializedName("serviceorderdetailinfo")
    @Expose
    private ArrayList<Object> serviceorderdetailinfo = null;
    @SerializedName("isserviceorderpaymentdetail")
    @Expose
    private Object isserviceorderpaymentdetail;
    @SerializedName("serviceorderpaymentdetailinfo")
    @Expose
    private ArrayList<Object> serviceorderpaymentdetailinfo = null;

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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getShoweditbutton() {
        return showeditbutton;
    }

    public void setShoweditbutton(String showeditbutton) {
        this.showeditbutton = showeditbutton;
    }

    public String getShowcancelbutton() {
        return showcancelbutton;
    }

    public void setShowcancelbutton(String showcancelbutton) {
        this.showcancelbutton = showcancelbutton;
    }

    public String getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus;
    }

    public String getOrderstatuscolor() {
        return orderstatuscolor;
    }

    public void setOrderstatuscolor(String orderstatuscolor) {
        this.orderstatuscolor = orderstatuscolor;
    }

    public Object getIsserviceorderdetail() {
        return isserviceorderdetail;
    }

    public void setIsserviceorderdetail(Object isserviceorderdetail) {
        this.isserviceorderdetail = isserviceorderdetail;
    }

    public ArrayList<Object> getServiceorderdetailinfo() {
        return serviceorderdetailinfo;
    }

    public void setServiceorderdetailinfo(ArrayList<Object> serviceorderdetailinfo) {
        this.serviceorderdetailinfo = serviceorderdetailinfo;
    }

    public Object getIsserviceorderpaymentdetail() {
        return isserviceorderpaymentdetail;
    }

    public void setIsserviceorderpaymentdetail(Object isserviceorderpaymentdetail) {
        this.isserviceorderpaymentdetail = isserviceorderpaymentdetail;
    }

    public ArrayList<Object> getServiceorderpaymentdetailinfo() {
        return serviceorderpaymentdetailinfo;
    }

    public void setServiceorderpaymentdetailinfo(ArrayList<Object> serviceorderpaymentdetailinfo) {
        this.serviceorderpaymentdetailinfo = serviceorderpaymentdetailinfo;
    }

    public String getInvoicepdfurl() {
        return invoicepdfurl;
    }

    public void setInvoicepdfurl(String invoicepdfurl) {
        this.invoicepdfurl = invoicepdfurl;
    }
}
