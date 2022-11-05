package com.instanceit.alhadafpos.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SummaryReport {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("itemname")
    @Expose
    private String itemname;
    @SerializedName("sale_qty")
    @Expose
    private String saleQty;
    @SerializedName("return_qty")
    @Expose
    private String returnQty;
    @SerializedName("sale_amount")
    @Expose
    private String saleAmount;

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

    public String getSaleQty() {
        return saleQty;
    }

    public void setSaleQty(String saleQty) {
        this.saleQty = saleQty;
    }

    public String getReturnQty() {
        return returnQty;
    }

    public void setReturnQty(String returnQty) {
        this.returnQty = returnQty;
    }

    public String getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(String saleAmount) {
        this.saleAmount = saleAmount;
    }
}
