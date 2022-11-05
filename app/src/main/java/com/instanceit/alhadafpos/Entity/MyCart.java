package com.instanceit.alhadafpos.Entity;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import io.michaelrocks.paranoid.Obfuscate;

@Obfuscate
public class MyCart {

    @SerializedName("categoryid")
    @Expose
    private String categoryid;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("subcategoryid")
    @Expose
    private String subcategoryid;
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
    private Integer qty = 0;
    @SerializedName("summarydetail")
    @Expose
    private ArrayList<SummaryDetail> summaryDetails = new ArrayList<>();
    private Integer temp_remainqty = 0;


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

    public String getSubcategoryid() {
        return subcategoryid;
    }

    public void setSubcategoryid(String subcategoryid) {
        this.subcategoryid = subcategoryid;
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

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public ArrayList<SummaryDetail> getSummaryDetails() {
        return summaryDetails;
    }

    public void setSummaryDetails(ArrayList<SummaryDetail> summaryDetails) {
        this.summaryDetails = summaryDetails;
    }

    public Integer getTemp_remainqty() {
        return temp_remainqty;
    }

    public void setTemp_remainqty(Integer temp_remainqty) {
        this.temp_remainqty = temp_remainqty;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        MyCart model = (MyCart) obj;
        if (this.itemid.equals(model.getItemid())) {
            return true;
        } else {
            return false;
        }
    }
}
