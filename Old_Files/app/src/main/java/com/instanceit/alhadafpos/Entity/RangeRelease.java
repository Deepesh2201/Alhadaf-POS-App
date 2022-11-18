package com.instanceit.alhadafpos.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RangeRelease {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("personname")
    @Expose
    private String personname;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("middlename")
    @Expose
    private String middlename;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("profileimg")
    @Expose
    private String profileimg;
    @SerializedName("rangeassignid")
    @Expose
    private String rangeassignid;
    @SerializedName("rangeid")
    @Expose
    private String rangeid;
    @SerializedName("rangename")
    @Expose
    private String rangename;
    @SerializedName("laneid")
    @Expose
    private String laneid;
    @SerializedName("lanename")
    @Expose
    private String lanename;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("isreleased")
    @Expose
    private String isreleased;
    @SerializedName("releasestatus")
    @Expose
    private String releasestatus;
    @SerializedName("releasestatuscolor")
    @Expose
    private String releasestatuscolor;
    @SerializedName("isitemdetail")
    @Expose
    private Integer isitemdetail;
    @SerializedName("itemdetail")
    @Expose
    private ArrayList<Storeinstructiondatum> itemdetail = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonname() {
        return personname;
    }

    public void setPersonname(String personname) {
        this.personname = personname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public String getProfileimg() {
        return profileimg;
    }

    public void setProfileimg(String profileimg) {
        this.profileimg = profileimg;
    }

    public String getRangeassignid() {
        return rangeassignid;
    }

    public void setRangeassignid(String rangeassignid) {
        this.rangeassignid = rangeassignid;
    }

    public String getRangeid() {
        return rangeid;
    }

    public void setRangeid(String rangeid) {
        this.rangeid = rangeid;
    }

    public String getRangename() {
        return rangename;
    }

    public void setRangename(String rangename) {
        this.rangename = rangename;
    }

    public String getLaneid() {
        return laneid;
    }

    public void setLaneid(String laneid) {
        this.laneid = laneid;
    }

    public String getLanename() {
        return lanename;
    }

    public void setLanename(String lanename) {
        this.lanename = lanename;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIsreleased() {
        return isreleased;
    }

    public void setIsreleased(String isreleased) {
        this.isreleased = isreleased;
    }

    public String getReleasestatus() {
        return releasestatus;
    }

    public void setReleasestatus(String releasestatus) {
        this.releasestatus = releasestatus;
    }

    public String getReleasestatuscolor() {
        return releasestatuscolor;
    }

    public void setReleasestatuscolor(String releasestatuscolor) {
        this.releasestatuscolor = releasestatuscolor;
    }

    public Integer getIsitemdetail() {
        return isitemdetail;
    }

    public void setIsitemdetail(Integer isitemdetail) {
        this.isitemdetail = isitemdetail;
    }

    public ArrayList<Storeinstructiondatum> getItemdetail() {
        return itemdetail;
    }

    public void setItemdetail(ArrayList<Storeinstructiondatum> itemdetail) {
        this.itemdetail = itemdetail;
    }

}
