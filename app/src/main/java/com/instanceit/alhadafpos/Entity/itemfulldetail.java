package com.instanceit.alhadafpos.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class itemfulldetail {
    @SerializedName("id")
    @Expose
    private String id;
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
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("usedqty")
    @Expose
    private String usedqty;
    @SerializedName("remainqty")
    @Expose
    private String remainqty;
    @SerializedName("durationid")
    @Expose
    private String durationid;
    @SerializedName("durationname")
    @Expose
    private String durationname;
    @SerializedName("durationdays")
    @Expose
    private String durationdays;
    @SerializedName("discount")
    @Expose
    private String discount;
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
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("typename")
    @Expose
    private String typename;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getUsedqty() {
        return usedqty;
    }

    public void setUsedqty(String usedqty) {
        this.usedqty = usedqty;
    }

    public String getRemainqty() {
        return remainqty;
    }

    public void setRemainqty(String remainqty) {
        this.remainqty = remainqty;
    }

    public String getDurationid() {
        return durationid;
    }

    public void setDurationid(String durationid) {
        this.durationid = durationid;
    }

    public String getDurationname() {
        return durationname;
    }

    public void setDurationname(String durationname) {
        this.durationname = durationname;
    }

    public String getDurationdays() {
        return durationdays;
    }

    public void setDurationdays(String durationdays) {
        this.durationdays = durationdays;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
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
}
