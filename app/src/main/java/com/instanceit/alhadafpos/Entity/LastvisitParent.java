package com.instanceit.alhadafpos.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import io.michaelrocks.paranoid.Obfuscate;

@Obfuscate
public class LastvisitParent {
    @SerializedName("categoryid")
    @Expose
    private String categoryid;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("islastvisititem")
    @Expose
    private Integer islastvisititem;
    @SerializedName("lastvisititem")
    @Expose
    private ArrayList<Lastvisititem> lastvisititem = null;

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

    public Integer getIslastvisititem() {
        return islastvisititem;
    }

    public void setIslastvisititem(Integer islastvisititem) {
        this.islastvisititem = islastvisititem;
    }

    public ArrayList<Lastvisititem> getLastvisititem() {
        return lastvisititem;
    }

    public void setLastvisititem(ArrayList<Lastvisititem> lastvisititem) {
        this.lastvisititem = lastvisititem;
    }
}
