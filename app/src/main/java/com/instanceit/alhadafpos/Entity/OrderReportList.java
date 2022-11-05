package com.instanceit.alhadafpos.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OrderReportList {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("invoiceno")
    @Expose
    private String invoiceno;
    @SerializedName("customername")
    @Expose
    private String customername;
    @SerializedName("entryusername")
    @Expose
    private String entryusername;
    @SerializedName("totalqty")
    @Expose
    private Integer totalqty;
    @SerializedName("totaltaxableamt")
    @Expose
    private Double totaltaxableamt;
    @SerializedName("totaltaxamt")
    @Expose
    private Double totaltaxamt;
    @SerializedName("totalamt")
    @Expose
    private Double totalamt;
    @SerializedName("entry_date")
    @Expose
    private String entryDate;
    @SerializedName("agencyfullname")
    @Expose
    private String agencyfullname;
    @SerializedName("pnrno")
    @Expose
    private String pnrno;
    @SerializedName("itemflag")
    @Expose
    private Integer itemflag;
    @SerializedName("item")
    @Expose
    private ArrayList<OrderItem> item = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInvoiceno() {
        return invoiceno;
    }

    public void setInvoiceno(String invoiceno) {
        this.invoiceno = invoiceno;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getEntryusername() {
        return entryusername;
    }

    public void setEntryusername(String entryusername) {
        this.entryusername = entryusername;
    }

    public Integer getTotalqty() {
        return totalqty;
    }

    public void setTotalqty(Integer totalqty) {
        this.totalqty = totalqty;
    }

    public Double getTotaltaxableamt() {
        return totaltaxableamt;
    }

    public void setTotaltaxableamt(Double totaltaxableamt) {
        this.totaltaxableamt = totaltaxableamt;
    }

    public Double getTotaltaxamt() {
        return totaltaxamt;
    }

    public void setTotaltaxamt(Double totaltaxamt) {
        this.totaltaxamt = totaltaxamt;
    }

    public Double getTotalamt() {
        return totalamt;
    }

    public void setTotalamt(Double totalamt) {
        this.totalamt = totalamt;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public ArrayList<OrderItem> getItem() {
        return item;
    }

    public void setItem(ArrayList<OrderItem> item) {
        this.item = item;
    }

    public String getAgencyfullname() {
        return agencyfullname;
    }

    public void setAgencyfullname(String agencyfullname) {
        this.agencyfullname = agencyfullname;
    }

    public String getPnrno() {
        return pnrno;
    }

    public void setPnrno(String pnrno) {
        this.pnrno = pnrno;
    }

    public Integer getItemflag() {
        return itemflag;
    }

    public void setItemflag(Integer itemflag) {
        this.itemflag = itemflag;
    }
}
