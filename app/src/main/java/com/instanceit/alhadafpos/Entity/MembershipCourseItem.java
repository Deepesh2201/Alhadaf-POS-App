package com.instanceit.alhadafpos.Entity;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MembershipCourseItem {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("itemname")
    @Expose
    private String itemname;
    @SerializedName("itemno")
    @Expose
    private String itemno;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("taxtypename")
    @Expose
    private String taxtypename;
    @SerializedName("taxtype")
    @Expose
    private String taxtype;
    @SerializedName("sgst")
    @Expose
    private String sgst;
    @SerializedName("cgst")
    @Expose
    private String cgst;
    @SerializedName("igst")
    @Expose
    private String igst;
    @SerializedName("descr")
    @Expose
    private String descr;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("durationname")
    @Expose
    private String durationname;
    @SerializedName("strduration")
    @Expose
    private String strduration;
    @SerializedName("strvalidityduration")
    @Expose
    private String strvalidityduration;
    @SerializedName("noofstudent")
    @Expose
    private String noofstudent;
    @SerializedName("strnoofstudent")
    @Expose
    private String strnoofstudent;
    @SerializedName("iconimg")
    @Expose
    private String iconimg;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("iscourse")
    @Expose
    private String iscourse;
    @SerializedName("attributedetail")
    @Expose
    private ArrayList<Attributedetail> attributedetail = null;

    @SerializedName("finalprice")
    @Expose
    private Double finalprice;
    @SerializedName("taxable")
    @Expose
    private Double taxable;
    @SerializedName("igsttaxamt")
    @Expose
    private Double igsttaxamt;
    @SerializedName("cgsttaxamt")
    @Expose
    private Double cgsttaxamt;
    @SerializedName("sgsttaxamt")
    @Expose
    private Double sgsttaxamt;
    @SerializedName("iscoursebenefit")
    @Expose
    private String iscoursebenefit;
    @SerializedName("iscoursedisplaydata")
    @Expose
    private String iscoursedisplaydata;
    @SerializedName("couponamount")
    @Expose
    private Double couponamount = 0.0;
    @SerializedName("type")
    @Expose
    private String type;

    int isadded;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIsadded() {
        return isadded;
    }

    public void setIsadded(int isadded) {
        this.isadded = isadded;
    }

    public String getDurationname() {
        return durationname;
    }

    public void setDurationname(String durationname) {
        this.durationname = durationname;
    }

    public String getStrduration() {
        return strduration;
    }

    public void setStrduration(String strduration) {
        this.strduration = strduration;
    }

    public String getStrvalidityduration() {
        return strvalidityduration;
    }

    public void setStrvalidityduration(String strvalidityduration) {
        this.strvalidityduration = strvalidityduration;
    }

    public String getNoofstudent() {
        return noofstudent;
    }

    public void setNoofstudent(String noofstudent) {
        this.noofstudent = noofstudent;
    }

    public String getStrnoofstudent() {
        return strnoofstudent;
    }

    public void setStrnoofstudent(String strnoofstudent) {
        this.strnoofstudent = strnoofstudent;
    }

    public String getIconimg() {
        return iconimg;
    }

    public void setIconimg(String iconimg) {
        this.iconimg = iconimg;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIscourse() {
        return iscourse;
    }

    public void setIscourse(String iscourse) {
        this.iscourse = iscourse;
    }

    public ArrayList<Attributedetail> getAttributedetail() {
        return attributedetail;
    }

    public void setAttributedetail(ArrayList<Attributedetail> attributedetail) {
        this.attributedetail = attributedetail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getItemno() {
        return itemno;
    }

    public void setItemno(String itemno) {
        this.itemno = itemno;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTaxtypename() {
        return taxtypename;
    }

    public void setTaxtypename(String taxtypename) {
        this.taxtypename = taxtypename;
    }

    public String getTaxtype() {
        return taxtype;
    }

    public void setTaxtype(String taxtype) {
        this.taxtype = taxtype;
    }

    public String getSgst() {
        return sgst;
    }

    public void setSgst(String sgst) {
        this.sgst = sgst;
    }

    public String getCgst() {
        return cgst;
    }

    public void setCgst(String cgst) {
        this.cgst = cgst;
    }

    public String getIgst() {
        return igst;
    }

    public void setIgst(String igst) {
        this.igst = igst;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Double getFinalprice() {
        return finalprice;
    }

    public void setFinalprice(Double finalprice) {
        this.finalprice = finalprice;
    }

    public Double getTaxable() {
        return taxable;
    }

    public void setTaxable(Double taxable) {
        this.taxable = taxable;
    }

    public Double getIgsttaxamt() {
        return igsttaxamt;
    }

    public void setIgsttaxamt(Double igsttaxamt) {
        this.igsttaxamt = igsttaxamt;
    }

    public Double getCgsttaxamt() {
        return cgsttaxamt;
    }

    public void setCgsttaxamt(Double cgsttaxamt) {
        this.cgsttaxamt = cgsttaxamt;
    }

    public Double getSgsttaxamt() {
        return sgsttaxamt;
    }

    public void setSgsttaxamt(Double sgsttaxamt) {
        this.sgsttaxamt = sgsttaxamt;
    }

    public String getIscoursebenefit() {
        return iscoursebenefit;
    }

    public void setIscoursebenefit(String iscoursebenefit) {
        this.iscoursebenefit = iscoursebenefit;
    }

    public String getIscoursedisplaydata() {
        return iscoursedisplaydata;
    }

    public void setIscoursedisplaydata(String iscoursedisplaydata) {
        this.iscoursedisplaydata = iscoursedisplaydata;
    }

    public Double getCouponamount() {
        return couponamount;
    }

    public void setCouponamount(Double couponamount) {
        this.couponamount = couponamount;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        MembershipCourseItem model = (MembershipCourseItem) obj;
        if (this.id.equals(model.getId())) {
            return true;
        } else {
            return false;
        }
    }

}
