package com.instanceit.alhadafpos.Entity;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.michaelrocks.paranoid.Obfuscate;

@Obfuscate
public class SummaryDetail {
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("typename")
    @Expose
    private String typename;
    @SerializedName("oidid")
    @Expose
    private String oidid;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("discount")
    @Expose
    private Integer discount=0;
    @SerializedName("discountamt")
    @Expose
    private Double discountamt;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("taxable")
    @Expose
    private Double taxable;
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
    @SerializedName("sgsttaxamt")
    @Expose
    private Double sgsttaxamt;
    @SerializedName("cgsttaxamt")
    @Expose
    private Double cgsttaxamt;
    @SerializedName("igsttaxamt")
    @Expose
    private Double igsttaxamt;
    @SerializedName("finalprice")
    @Expose
    private Double finalprice;


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

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = (int) discount;
    }

    public Double getDiscountamt() {
        return discountamt;
    }

    public void setDiscountamt(Double discountamt) {
        this.discountamt = discountamt;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTaxable() {
        return taxable;
    }

    public void setTaxable(Double taxable) {
        this.taxable = taxable;
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

    public Double getSgsttaxamt() {
        return sgsttaxamt;
    }

    public void setSgsttaxamt(Double sgsttaxamt) {
        this.sgsttaxamt = sgsttaxamt;
    }

    public Double getCgsttaxamt() {
        return cgsttaxamt;
    }

    public void setCgsttaxamt(Double cgsttaxamt) {
        this.cgsttaxamt = cgsttaxamt;
    }

    public Double getIgsttaxamt() {
        return igsttaxamt;
    }

    public void setIgsttaxamt(Double igsttaxamt) {
        this.igsttaxamt = igsttaxamt;
    }

    public Double getFinalprice() {
        return finalprice;
    }

    public void setFinalprice(Double finalprice) {
        this.finalprice = finalprice;
    }


    @Override
    public boolean equals(@Nullable Object obj) {
        SummaryDetail model = (SummaryDetail) obj;
        if (this.oidid.equals(model.getOidid())) {
            return true;
        } else {
            return false;
        }
    }

}
