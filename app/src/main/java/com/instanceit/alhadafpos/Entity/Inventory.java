package com.instanceit.alhadafpos.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import io.michaelrocks.paranoid.Obfuscate;

@Obfuscate
public class Inventory {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("storeid")
    @Expose
    private String storeid;
    @SerializedName("memberid")
    @Expose
    private String memberid;
    @SerializedName("membername")
    @Expose
    private String membername;
    @SerializedName("membercontact")
    @Expose
    private String membercontact;
    @SerializedName("orderdate")
    @Expose
    private String orderdate;
    @SerializedName("comment")
    @Expose
    private String comment;
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
    @SerializedName("isreturnorderdetail")
    @Expose
    private String isreturnorderdetail;
    @SerializedName("returnorderdetailinfo")
    @Expose
    private ArrayList<Returnorderdetailinfo> returnorderdetailinfo = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoreid() {
        return storeid;
    }

    public void setStoreid(String storeid) {
        this.storeid = storeid;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
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

    public String getIsreturnorderdetail() {
        return isreturnorderdetail;
    }

    public void setIsreturnorderdetail(String isreturnorderdetail) {
        this.isreturnorderdetail = isreturnorderdetail;
    }

    public ArrayList<Returnorderdetailinfo> getReturnorderdetailinfo() {
        return returnorderdetailinfo;
    }

    public void setReturnorderdetailinfo(ArrayList<Returnorderdetailinfo> returnorderdetailinfo) {
        this.returnorderdetailinfo = returnorderdetailinfo;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
