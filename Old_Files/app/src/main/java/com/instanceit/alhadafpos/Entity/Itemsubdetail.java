package com.instanceit.alhadafpos.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Itemsubdetail {
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("discount")
    @Expose
    private Double discount;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("taxtypename")
    @Expose
    private String taxtypename;
    @SerializedName("taxtype")
    @Expose
    private String taxtype;
    @SerializedName("sgst")
    @Expose
    private Double sgst;
    @SerializedName("cgst")
    @Expose
    private Double cgst;
    @SerializedName("igst")
    @Expose
    private Double igst;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("typename")
    @Expose
    private String typename;
    @SerializedName("oidid")
    @Expose
    private String oidid;
//    @SerializedName("usedqty")
//    @Expose
//    private String usedqty;
    @SerializedName("remainqty")
    @Expose
    private Integer remainqty;
    @SerializedName("durationid")
    @Expose
    private String durationid;
    @SerializedName("durationname")
    @Expose
    private String durationname;
    @SerializedName("durationdays")
    @Expose
    private String durationdays;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("baseqty")
    @Expose
    private String baseqty;


    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
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

    public String getOidid() {
        return oidid;
    }

    public void setOidid(String oidid) {
        this.oidid = oidid;
    }

//    public String getUsedqty() {
//        return usedqty;
//    }
//
//    public void setUsedqty(String usedqty) {
//        this.usedqty = usedqty;
//    }
//
//    public Integer getRemainqty() {
//        return remainqty;
//    }
//
//    public void setRemainqty(Integer remainqty) {
//        this.remainqty = remainqty;
//    }

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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getRemainqty() {
        return remainqty;
    }

    public void setRemainqty(Integer remainqty) {
        this.remainqty = remainqty;
    }

    public String getBaseqty() {
        return baseqty;
    }

    public void setBaseqty(String baseqty) {
        this.baseqty = baseqty;
    }
}
