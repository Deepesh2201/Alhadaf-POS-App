package com.instanceit.alhadafpos.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class IssueServiceOrder {
    @SerializedName("id")
    @Expose
    private String id;
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
    @SerializedName("orderdate")
    @Expose
    private String orderdate;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("isissueorderdetail")
    @Expose
    private String isissueorderdetail;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("issueorderdetailinfo")
    @Expose
    private ArrayList<Issueorderdetailinfo> issueorderdetailinfo = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getIsissueorderdetail() {
        return isissueorderdetail;
    }

    public void setIsissueorderdetail(String isissueorderdetail) {
        this.isissueorderdetail = isissueorderdetail;
    }

    public ArrayList<Issueorderdetailinfo> getIssueorderdetailinfo() {
        return issueorderdetailinfo;
    }

    public void setIssueorderdetailinfo(ArrayList<Issueorderdetailinfo> issueorderdetailinfo) {
        this.issueorderdetailinfo = issueorderdetailinfo;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
