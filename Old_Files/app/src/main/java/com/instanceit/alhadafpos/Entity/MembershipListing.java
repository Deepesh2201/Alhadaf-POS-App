package com.instanceit.alhadafpos.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MembershipListing {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("transactionid")
    @Expose
    private String transactionid;
    @SerializedName("orderno")
    @Expose
    private String orderno;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("membername")
    @Expose
    private String membername;
    @SerializedName("membercontact")
    @Expose
    private String membercontact;
    @SerializedName("totalamount")
    @Expose
    private String totalamount;
    @SerializedName("couponapply")
    @Expose
    private String couponapply;
    @SerializedName("couponid")
    @Expose
    private String couponid;
    @SerializedName("couponcode")
    @Expose
    private String couponcode;
    @SerializedName("couponamount")
    @Expose
    private String couponamount;
    @SerializedName("totaltaxableamt")
    @Expose
    private String totaltaxableamt;
    @SerializedName("totaltax")
    @Expose
    private String totaltax;
    @SerializedName("totalpaid")
    @Expose
    private String totalpaid;
    @SerializedName("iscancel")
    @Expose
    private String iscancel;
    @SerializedName("strcancel")
    @Expose
    private String strcancel;
    @SerializedName("ofulldate")
    @Expose
    private String ofulldate;
    @SerializedName("entrypersonname")
    @Expose
    private String entrypersonname;
    @SerializedName("entrypersoncontact")
    @Expose
    private String entrypersoncontact;
    @SerializedName("invoicepdfurl")
    @Expose
    private String invoicepdfurl;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("orderstatus")
    @Expose
    private String orderstatus;
    @SerializedName("orderstatuscolor")
    @Expose
    private String orderstatuscolor;
    @SerializedName("orderdetailinfo")
    @Expose
    private ArrayList<Orderdetailinfo> orderdetailinfo = null;

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

    public String getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
    }

    public String getCouponapply() {
        return couponapply;
    }

    public void setCouponapply(String couponapply) {
        this.couponapply = couponapply;
    }

    public String getCouponid() {
        return couponid;
    }

    public void setCouponid(String couponid) {
        this.couponid = couponid;
    }

    public String getCouponcode() {
        return couponcode;
    }

    public void setCouponcode(String couponcode) {
        this.couponcode = couponcode;
    }

    public String getCouponamount() {
        return couponamount;
    }

    public void setCouponamount(String couponamount) {
        this.couponamount = couponamount;
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

    public String getIscancel() {
        return iscancel;
    }

    public void setIscancel(String iscancel) {
        this.iscancel = iscancel;
    }

    public String getStrcancel() {
        return strcancel;
    }

    public void setStrcancel(String strcancel) {
        this.strcancel = strcancel;
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

    public String getInvoicepdfurl() {
        return invoicepdfurl;
    }

    public void setInvoicepdfurl(String invoicepdfurl) {
        this.invoicepdfurl = invoicepdfurl;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
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

    public ArrayList<Orderdetailinfo> getOrderdetailinfo() {
        return orderdetailinfo;
    }

    public void setOrderdetailinfo(ArrayList<Orderdetailinfo> orderdetailinfo) {
        this.orderdetailinfo = orderdetailinfo;
    }

}
