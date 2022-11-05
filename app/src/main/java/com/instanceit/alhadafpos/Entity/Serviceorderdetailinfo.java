package com.instanceit.alhadafpos.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Serviceorderdetailinfo {

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
    @SerializedName("issuedqty")
    @Expose
    private String issuedqty;
    @SerializedName("remainqty")
    @Expose
    private String remainqty;
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
    @SerializedName("discountper")
    @Expose
    private String discountper;
    @SerializedName("discountamt")
    @Expose
    private String discountamt;
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
    @SerializedName("showcancelbutton")
    @Expose
    private String showcancelbutton;
    @SerializedName("soitemstatus")
    @Expose
    private String soitemstatus;
    @SerializedName("soitemstatuscolor")
    @Expose
    private String soitemstatuscolor;

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

    public String getIssuedqty() {
        return issuedqty;
    }

    public void setIssuedqty(String issuedqty) {
        this.issuedqty = issuedqty;
    }

    public String getRemainqty() {
        return remainqty;
    }

    public void setRemainqty(String remainqty) {
        this.remainqty = remainqty;
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

    public String getDiscountper() {
        return discountper;
    }

    public void setDiscountper(String discountper) {
        this.discountper = discountper;
    }

    public String getDiscountamt() {
        return discountamt;
    }

    public void setDiscountamt(String discountamt) {
        this.discountamt = discountamt;
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

    public String getShowcancelbutton() {
        return showcancelbutton;
    }

    public void setShowcancelbutton(String showcancelbutton) {
        this.showcancelbutton = showcancelbutton;
    }

    public String getSoitemstatus() {
        return soitemstatus;
    }

    public void setSoitemstatus(String soitemstatus) {
        this.soitemstatus = soitemstatus;
    }

    public String getSoitemstatuscolor() {
        return soitemstatuscolor;
    }

    public void setSoitemstatuscolor(String soitemstatuscolor) {
        this.soitemstatuscolor = soitemstatuscolor;
    }

}
