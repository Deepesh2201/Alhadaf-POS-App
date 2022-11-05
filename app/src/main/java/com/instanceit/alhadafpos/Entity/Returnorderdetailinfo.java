package com.instanceit.alhadafpos.Entity;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.michaelrocks.paranoid.Obfuscate;

@Obfuscate
public class Returnorderdetailinfo {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("orderid")
    @Expose
    private String orderid;
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
    @SerializedName("oldqty")
    @Expose
    private String oldqty;

    int isitemadded = 0;

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

    public int getIsitemadded() {
        return isitemadded;
    }

    public void setIsitemadded(int isitemadded) {
        this.isitemadded = isitemadded;
    }

    public String getOldqty() {
        return oldqty;
    }

    public void setOldqty(String oldqty) {
        this.oldqty = oldqty;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Returnorderdetailinfo navChild = (Returnorderdetailinfo) obj;
        if (this.itemid.equals(navChild.getItemid())) {
            return true;
        } else {
            return false;
        }
    }
}
