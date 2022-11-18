package com.instanceit.alhadafpos.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OperationFlow {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("storeid")
    @Expose
    private String storeid;
    @SerializedName("storename")
    @Expose
    private String storename;
    @SerializedName("operationid")
    @Expose
    private String operationid;
    @SerializedName("operationname")
    @Expose
    private String operationname;
    @SerializedName("iscompulsory")
    @Expose
    private String iscompulsory;
    @SerializedName("iscompleted")
    @Expose
    private String iscompleted;
    @SerializedName("iscurrent")
    @Expose
    private String iscurrent;
    @SerializedName("displayorder")
    @Expose
    private String displayorder;
    @SerializedName("statuscolor")
    @Expose
    private String statuscolor;
    @SerializedName("isstoreinstruction")
    @Expose
    private String isstoreinstruction;
    @SerializedName("storeinstructiondata")
    @Expose
    private ArrayList<Storeinstructiondatum> storeinstructiondata = null;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoreid() {
        return storeid;
    }

    public void setStoreid(String storeid) {
        this.storeid = storeid;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }

    public String getOperationid() {
        return operationid;
    }

    public void setOperationid(String operationid) {
        this.operationid = operationid;
    }

    public String getOperationname() {
        return operationname;
    }

    public void setOperationname(String operationname) {
        this.operationname = operationname;
    }

    public String getIscompulsory() {
        return iscompulsory;
    }

    public void setIscompulsory(String iscompulsory) {
        this.iscompulsory = iscompulsory;
    }

    public String getIscompleted() {
        return iscompleted;
    }

    public String getIscurrent() {
        return iscurrent;
    }

    public void setIscurrent(String iscurrent) {
        this.iscurrent = iscurrent;
    }

    public void setIscompleted(String iscompleted) {
        this.iscompleted = iscompleted;
    }

    public String getDisplayorder() {
        return displayorder;
    }

    public void setDisplayorder(String displayorder) {
        this.displayorder = displayorder;
    }

    public String getStatuscolor() {
        return statuscolor;
    }

    public void setStatuscolor(String statuscolor) {
        this.statuscolor = statuscolor;
    }

    public String getIsstoreinstruction() {
        return isstoreinstruction;
    }

    public void setIsstoreinstruction(String isstoreinstruction) {
        this.isstoreinstruction = isstoreinstruction;
    }

    public ArrayList<Storeinstructiondatum> getStoreinstructiondata() {
        return storeinstructiondata;
    }

    public void setStoreinstructiondata(ArrayList<Storeinstructiondatum> storeinstructiondata) {
        this.storeinstructiondata = storeinstructiondata;
    }
}
