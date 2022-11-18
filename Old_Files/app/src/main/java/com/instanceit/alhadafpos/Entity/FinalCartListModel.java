package com.instanceit.alhadafpos.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FinalCartListModel {

    @SerializedName("iss")
    @Expose
    private String iss;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("utypeid")
    @Expose
    private String utypeid;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("cmpid")
    @Expose
    private String cmpid;
    @SerializedName("branchid")
    @Expose
    private String branchid;
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("adlogin")
    @Expose
    private String adlogin;
    @SerializedName("unqkey")
    @Expose
    private String unqkey;

    @SerializedName("yearid")
    @Expose
    private String yearid;

    @SerializedName("yearname")
    @Expose
    private String yearname;

    @SerializedName("activeyearid")
    @Expose
    private String activeyearid;

    @SerializedName("couponapply")
    @Expose
    private Integer couponapply = 0;

    @SerializedName("couponid")
    @Expose
    private String couponid = "";

    @SerializedName("couponcode")
    @Expose
    private String couponcode = "";

    @SerializedName("coupontype")
    @Expose
    private String coupontype = "";

    @SerializedName("couponamount")
    @Expose
    private Double couponamount = 0.0;

    @SerializedName("couponpercent")
    @Expose
    private String couponpercent = "";

    @SerializedName("cartiteminfo")
    @Expose
    private ArrayList<MembershipCourseItem> cartiteminfo = null;


    public String getIss() {
        return iss;
    }

    public void setIss(String iss) {
        this.iss = iss;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUtypeid() {
        return utypeid;
    }

    public void setUtypeid(String utypeid) {
        this.utypeid = utypeid;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCmpid() {
        return cmpid;
    }

    public void setCmpid(String cmpid) {
        this.cmpid = cmpid;
    }

    public String getBranchid() {
        return branchid;
    }

    public void setBranchid(String branchid) {
        this.branchid = branchid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAdlogin() {
        return adlogin;
    }

    public void setAdlogin(String adlogin) {
        this.adlogin = adlogin;
    }

    public String getUnqkey() {
        return unqkey;
    }

    public void setUnqkey(String unqkey) {
        this.unqkey = unqkey;
    }

    public String getYearid() {
        return yearid;
    }

    public void setYearid(String yearid) {
        this.yearid = yearid;
    }

    public String getYearname() {
        return yearname;
    }

    public void setYearname(String yearname) {
        this.yearname = yearname;
    }

    public String getActiveyearid() {
        return activeyearid;
    }

    public void setActiveyearid(String activeyearid) {
        this.activeyearid = activeyearid;
    }

    public Integer getCouponapply() {
        return couponapply;
    }

    public void setCouponapply(Integer couponapply) {
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

    public String getCoupontype() {
        return coupontype;
    }

    public void setCoupontype(String coupontype) {
        this.coupontype = coupontype;
    }

    public Double getCouponamount() {
        return couponamount;
    }

    public void setCouponamount(Double couponamount) {
        this.couponamount = couponamount;
    }

    public String getCouponpercent() {
        return couponpercent;
    }

    public void setCouponpercent(String couponpercent) {
        this.couponpercent = couponpercent;
    }

    public ArrayList<MembershipCourseItem> getCartiteminfo() {
        return cartiteminfo;
    }

    public void setCartiteminfo(ArrayList<MembershipCourseItem> cartiteminfo) {
        this.cartiteminfo = cartiteminfo;
    }


}
