package com.instanceit.alhadafpos.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Orderdetailinfo {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("orderid")
    @Expose
    private String orderid;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("typename")
    @Expose
    private String typename;
    @SerializedName("itemid")
    @Expose
    private String itemid;
    @SerializedName("itemname")
    @Expose
    private String itemname;
    @SerializedName("durationday")
    @Expose
    private String durationday;
    @SerializedName("durationname")
    @Expose
    private String durationname;
    @SerializedName("strvalidityduration")
    @Expose
    private String strvalidityduration;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("courseduration")
    @Expose
    private String courseduration;
    @SerializedName("noofstudent")
    @Expose
    private String noofstudent;
    @SerializedName("startdate")
    @Expose
    private String startdate;
    @SerializedName("expirydate")
    @Expose
    private String expirydate;
    @SerializedName("n_expirydate")
    @Expose
    private String nExpirydate;
    @SerializedName("taxtype")
    @Expose
    private String taxtype;
    @SerializedName("taxtypename")
    @Expose
    private String taxtypename;
    @SerializedName("sgst")
    @Expose
    private String sgst;
    @SerializedName("cgst")
    @Expose
    private String cgst;
    @SerializedName("igst")
    @Expose
    private String igst;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("couponamount")
    @Expose
    private String couponamount;
    @SerializedName("taxable")
    @Expose
    private String taxable;
    @SerializedName("sgsttaxamt")
    @Expose
    private String sgsttaxamt;
    @SerializedName("cgsttaxamt")
    @Expose
    private String cgsttaxamt;
    @SerializedName("igsttaxamt")
    @Expose
    private String igsttaxamt;
    @SerializedName("finalprice")
    @Expose
    private String finalprice;
    @SerializedName("strexpire")
    @Expose
    private String strexpire;
    @SerializedName("strexpirecolor")
    @Expose
    private String strexpirecolor;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
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

    public String getDurationday() {
        return durationday;
    }

    public void setDurationday(String durationday) {
        this.durationday = durationday;
    }

    public String getDurationname() {
        return durationname;
    }

    public void setDurationname(String durationname) {
        this.durationname = durationname;
    }

    public String getStrvalidityduration() {
        return strvalidityduration;
    }

    public void setStrvalidityduration(String strvalidityduration) {
        this.strvalidityduration = strvalidityduration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCourseduration() {
        return courseduration;
    }

    public void setCourseduration(String courseduration) {
        this.courseduration = courseduration;
    }

    public String getNoofstudent() {
        return noofstudent;
    }

    public void setNoofstudent(String noofstudent) {
        this.noofstudent = noofstudent;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
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

    public String getTaxtype() {
        return taxtype;
    }

    public void setTaxtype(String taxtype) {
        this.taxtype = taxtype;
    }

    public String getTaxtypename() {
        return taxtypename;
    }

    public void setTaxtypename(String taxtypename) {
        this.taxtypename = taxtypename;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCouponamount() {
        return couponamount;
    }

    public void setCouponamount(String couponamount) {
        this.couponamount = couponamount;
    }

    public String getTaxable() {
        return taxable;
    }

    public void setTaxable(String taxable) {
        this.taxable = taxable;
    }

    public String getSgsttaxamt() {
        return sgsttaxamt;
    }

    public void setSgsttaxamt(String sgsttaxamt) {
        this.sgsttaxamt = sgsttaxamt;
    }

    public String getCgsttaxamt() {
        return cgsttaxamt;
    }

    public void setCgsttaxamt(String cgsttaxamt) {
        this.cgsttaxamt = cgsttaxamt;
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

    public String getStrexpire() {
        return strexpire;
    }

    public void setStrexpire(String strexpire) {
        this.strexpire = strexpire;
    }

    public String getStrexpirecolor() {
        return strexpirecolor;
    }

    public void setStrexpirecolor(String strexpirecolor) {
        this.strexpirecolor = strexpirecolor;
    }


}
