package com.instanceit.alhadafpos.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Storeinstructiondatum {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("iscompleted")
    @Expose
    private String iscompleted;
    @SerializedName("instructionstatus")
    @Expose
    private String instructionstatus;
    @SerializedName("qty")
    @Expose
    private String qty;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIscompleted() {
        return iscompleted;
    }

    public void setIscompleted(String iscompleted) {
        this.iscompleted = iscompleted;
    }

    public String getInstructionstatus() {
        return instructionstatus;
    }

    public void setInstructionstatus(String instructionstatus) {
        this.instructionstatus = instructionstatus;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
