package com.instanceit.alhadafpos.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Member360 {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("personname")
    @Expose
    private String personname;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("qataridno")
    @Expose
    private String qataridno;
    @SerializedName("passportidno")
    @Expose
    private String passportidno;
    @SerializedName("passportidexpiry")
    @Expose
    private String passportidexpiry;
    @SerializedName("qataridexpiry")
    @Expose
    private String qataridexpiry;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("nationality")
    @Expose
    private String nationality;
    @SerializedName("companyname")
    @Expose
    private String companyname;
    @SerializedName("profileimg")
    @Expose
    private String profileimg;
    @SerializedName("ismshipdetail")
    @Expose
    private Integer ismshipdetail;
    @SerializedName("mshipdetail")
    @Expose
    private ArrayList<Mshipdetail> mshipdetail = null;
    @SerializedName("isitemdetail")
    @Expose
    private Integer isitemdetail;
    @SerializedName("itemdetail")
    @Expose
    private ArrayList<Items> itemdetail = null;
    @SerializedName("islastvisitcategory")
    @Expose
    private Integer islastvisitcategory;
    @SerializedName("lastvisitdate")
    @Expose
    private String lastvisitdate;
    @SerializedName("lastvisitcategory")
    @Expose
    private ArrayList<LastvisitParent> lastvisitcategory = null;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getQataridno() {
        return qataridno;
    }

    public void setQataridno(String qataridno) {
        this.qataridno = qataridno;
    }

    public String getQataridexpiry() {
        return qataridexpiry;
    }

    public void setQataridexpiry(String qataridexpiry) {
        this.qataridexpiry = qataridexpiry;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getProfileimg() {
        return profileimg;
    }

    public void setProfileimg(String profileimg) {
        this.profileimg = profileimg;
    }

    public Integer getIsmshipdetail() {
        return ismshipdetail;
    }

    public void setIsmshipdetail(Integer ismshipdetail) {
        this.ismshipdetail = ismshipdetail;
    }

    public ArrayList<Mshipdetail> getMshipdetail() {
        return mshipdetail;
    }

    public void setMshipdetail(ArrayList<Mshipdetail> mshipdetail) {
        this.mshipdetail = mshipdetail;
    }

    public Integer getIsitemdetail() {
        return isitemdetail;
    }

    public void setIsitemdetail(Integer isitemdetail) {
        this.isitemdetail = isitemdetail;
    }

    public ArrayList<Items> getItemdetail() {
        return itemdetail;
    }

    public void setItemdetail(ArrayList<Items> itemdetail) {
        this.itemdetail = itemdetail;
    }

    public Integer getIslastvisitcategory() {
        return islastvisitcategory;
    }

    public void setIslastvisitcategory(Integer islastvisitcategory) {
        this.islastvisitcategory = islastvisitcategory;
    }

    public ArrayList<LastvisitParent> getLastvisitcategory() {
        return lastvisitcategory;
    }

    public void setLastvisitcategory(ArrayList<LastvisitParent> lastvisitcategory) {
        this.lastvisitcategory = lastvisitcategory;
    }

    public String getLastvisitdate() {
        return lastvisitdate;
    }

    public void setLastvisitdate(String lastvisitdate) {
        this.lastvisitdate = lastvisitdate;
    }

    public String getPassportidno() {
        return passportidno;
    }

    public void setPassportidno(String passportidno) {
        this.passportidno = passportidno;
    }

    public String getPassportidexpiry() {
        return passportidexpiry;
    }

    public void setPassportidexpiry(String passportidexpiry) {
        this.passportidexpiry = passportidexpiry;
    }
}
