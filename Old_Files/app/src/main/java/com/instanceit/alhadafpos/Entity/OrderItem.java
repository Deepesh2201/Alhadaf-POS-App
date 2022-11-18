package com.instanceit.alhadafpos.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderItem {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("itemname")
    @Expose
    private String itemname;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("categoryid")
    @Expose
    private String categoryid;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("itemrate")
    @Expose
    private Double itemrate;
    @SerializedName("discount")
    @Expose
    private Double discount;
    @SerializedName("finalrate")
    @Expose
    private Double finalrate;
    @SerializedName("taxtype")
    @Expose
    private Integer taxtype;
    @SerializedName("taxableamt")
    @Expose
    private Double taxableamt;
    @SerializedName("taxamt")
    @Expose
    private Double taxamt;
    @SerializedName("totamt")
    @Expose
    private Double totamt;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("finaltotaltaxableamt")
    @Expose
    private Double finaltotaltaxableamt;
    @SerializedName("finaltotaltaxamt")
    @Expose
    private Double finaltotaltaxamt;
    @SerializedName("finaltotalamt")
    @Expose
    private Double finaltotalamt;
    @SerializedName("staxamt")
    @Expose
    private Double staxamt;
    @SerializedName("ctaxamt")
    @Expose
    private Double ctaxamt;
    @SerializedName("itaxamt")
    @Expose
    private Double itaxamt;
    @SerializedName("sgst")
    @Expose
    private Double sgst;
    @SerializedName("cgst")
    @Expose
    private Double cgst;
    @SerializedName("igst")
    @Expose
    private Double igst;
    @SerializedName("itemflag")
    @Expose
    private Integer itemflag;

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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getItemrate() {
        return itemrate;
    }

    public void setItemrate(Double itemrate) {
        this.itemrate = itemrate;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getFinalrate() {
        return finalrate;
    }

    public void setFinalrate(Double finalrate) {
        this.finalrate = finalrate;
    }

    public Integer getTaxtype() {
        return taxtype;
    }

    public void setTaxtype(Integer taxtype) {
        this.taxtype = taxtype;
    }

    public Double getTaxableamt() {
        return taxableamt;
    }

    public void setTaxableamt(Double taxableamt) {
        this.taxableamt = taxableamt;
    }

    public Double getTaxamt() {
        return taxamt;
    }

    public void setTaxamt(Double taxamt) {
        this.taxamt = taxamt;
    }

    public Double getTotamt() {
        return totamt;
    }

    public void setTotamt(Double totamt) {
        this.totamt = totamt;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Double getFinaltotaltaxableamt() {
        return finaltotaltaxableamt;
    }

    public void setFinaltotaltaxableamt(Double finaltotaltaxableamt) {
        this.finaltotaltaxableamt = finaltotaltaxableamt;
    }

    public Double getFinaltotaltaxamt() {
        return finaltotaltaxamt;
    }

    public void setFinaltotaltaxamt(Double finaltotaltaxamt) {
        this.finaltotaltaxamt = finaltotaltaxamt;
    }

    public Double getFinaltotalamt() {
        return finaltotalamt;
    }

    public void setFinaltotalamt(Double finaltotalamt) {
        this.finaltotalamt = finaltotalamt;
    }

    public Double getStaxamt() {
        return staxamt;
    }

    public void setStaxamt(Double staxamt) {
        this.staxamt = staxamt;
    }

    public Double getCtaxamt() {
        return ctaxamt;
    }

    public void setCtaxamt(Double ctaxamt) {
        this.ctaxamt = ctaxamt;
    }

    public Double getItaxamt() {
        return itaxamt;
    }

    public void setItaxamt(Double itaxamt) {
        this.itaxamt = itaxamt;
    }

    public Double getSgst() {
        return sgst;
    }

    public void setSgst(Double sgst) {
        this.sgst = sgst;
    }

    public Double getCgst() {
        return cgst;
    }

    public void setCgst(Double cgst) {
        this.cgst = cgst;
    }

    public Double getIgst() {
        return igst;
    }

    public void setIgst(Double igst) {
        this.igst = igst;
    }

    public Integer getItemflag() {
        return itemflag;
    }

    public void setItemflag(Integer itemflag) {
        this.itemflag = itemflag;
    }
}
