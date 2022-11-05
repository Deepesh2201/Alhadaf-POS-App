package com.instanceit.alhadafpos.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Itemdetail {
    @SerializedName("catid")
    @Expose
    private String catid;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("subcatid")
    @Expose
    private String subcatid;
    @SerializedName("subcategory")
    @Expose
    private String subcategory;
    @SerializedName("itemid")
    @Expose
    private String itemid;
    @SerializedName("itemname")
    @Expose
    private String itemname;
    @SerializedName("isitemsubdetail")
    @Expose
    private Integer isitemsubdetail;
    @SerializedName("itemsubdetail")
    @Expose
    private ArrayList<Itemsubdetail> itemsubdetail = null;

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("durationname")
    @Expose
    private String durationname;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("taxable")
    @Expose
    private String taxable;
    @SerializedName("igsttaxamt")
    @Expose
    private String igsttaxamt;
    @SerializedName("finalprice")
    @Expose
    private String finalprice;
    @SerializedName("igst")
    @Expose
    private String igst;
    @SerializedName("couponamount")
    @Expose
    private String couponamount;
    @SerializedName("expirydate")
    @Expose
    private String expirydate;
    @SerializedName("n_expirydate")
    @Expose
    private String nExpirydate;
    @SerializedName("strvalidityduration")
    @Expose
    private String strvalidityduration;
    @SerializedName("typename")
    @Expose
    private String typename;
    @SerializedName("isitemfulldetail")
    @Expose
    private String isitemfulldetail;
    @SerializedName("itemfulldetail")
    @Expose
    private ArrayList<itemfulldetail> itemfulldetail = null;
    @SerializedName("isitemwebsitedetail")
    @Expose
    private String isitemwebsitedetail;
    @SerializedName("itemwebsitedetail")
    @Expose
    private ArrayList<Itemwebsitedetail> itemwebsitedetail = null;
    @SerializedName("iscoursebenefitdetail")
    @Expose
    private String iscoursebenefitdetail;
    @SerializedName("coursebenefitdetail")
    @Expose
    private ArrayList<Coursebenefitdetail> coursebenefitdetail = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getDurationname() {
        return durationname;
    }

    public void setDurationname(String durationname) {
        this.durationname = durationname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTaxable() {
        return taxable;
    }

    public void setTaxable(String taxable) {
        this.taxable = taxable;
    }

    public String getIgsttaxamt() {
        return igsttaxamt;
    }

    public void setIgsttaxamt(String igsttaxamt) {
        this.igsttaxamt = igsttaxamt;
    }

    public String getFinalprice() {
        return finalprice;
    }

    public void setFinalprice(String finalprice) {
        this.finalprice = finalprice;
    }

    public String getIgst() {
        return igst;
    }

    public void setIgst(String igst) {
        this.igst = igst;
    }

    public String getCouponamount() {
        return couponamount;
    }

    public void setCouponamount(String couponamount) {
        this.couponamount = couponamount;
    }

    public String getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(String expirydate) {
        this.expirydate = expirydate;
    }

    public String getnExpirydate() {
        return nExpirydate;
    }

    public void setnExpirydate(String nExpirydate) {
        this.nExpirydate = nExpirydate;
    }

    public String getStrvalidityduration() {
        return strvalidityduration;
    }

    public void setStrvalidityduration(String strvalidityduration) {
        this.strvalidityduration = strvalidityduration;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getIsitemfulldetail() {
        return isitemfulldetail;
    }

    public void setIsitemfulldetail(String isitemfulldetail) {
        this.isitemfulldetail = isitemfulldetail;
    }

    public ArrayList<itemfulldetail> getItemfulldetail() {
        return itemfulldetail;
    }

    public void setItemfulldetail(ArrayList<itemfulldetail> itemfulldetail) {
        this.itemfulldetail = itemfulldetail;
    }

    public String getIsitemwebsitedetail() {
        return isitemwebsitedetail;
    }

    public void setIsitemwebsitedetail(String isitemwebsitedetail) {
        this.isitemwebsitedetail = isitemwebsitedetail;
    }

    public ArrayList<Itemwebsitedetail> getItemwebsitedetail() {
        return itemwebsitedetail;
    }

    public void setItemwebsitedetail(ArrayList<Itemwebsitedetail> itemwebsitedetail) {
        this.itemwebsitedetail = itemwebsitedetail;
    }

    public String getIscoursebenefitdetail() {
        return iscoursebenefitdetail;
    }

    public void setIscoursebenefitdetail(String iscoursebenefitdetail) {
        this.iscoursebenefitdetail = iscoursebenefitdetail;
    }

    public ArrayList<Coursebenefitdetail> getCoursebenefitdetail() {
        return coursebenefitdetail;
    }

    public void setCoursebenefitdetail(ArrayList<Coursebenefitdetail> coursebenefitdetail) {
        this.coursebenefitdetail = coursebenefitdetail;
    }


    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcatid() {
        return subcatid;
    }

    public void setSubcatid(String subcatid) {
        this.subcatid = subcatid;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public Integer getIsitemsubdetail() {
        return isitemsubdetail;
    }

    public void setIsitemsubdetail(Integer isitemsubdetail) {
        this.isitemsubdetail = isitemsubdetail;
    }

    public ArrayList<Itemsubdetail> getItemsubdetail() {
        return itemsubdetail;
    }

    public void setItemsubdetail(ArrayList<Itemsubdetail> itemsubdetail) {
        this.itemsubdetail = itemsubdetail;
    }
}
