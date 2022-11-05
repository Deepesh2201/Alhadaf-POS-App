package com.instanceit.alhadafpos.Entity;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Items {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("itemno")
    @Expose
    private String itemno;
    @SerializedName("oidid")
    @Expose
    private String oidid;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("typename")
    @Expose
    private String typename;
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
    private Double price;
    @SerializedName("discount")
    @Expose
    private Double discount = 0.0;
    @SerializedName("discountamt")
    @Expose
    private Double discountamt = 0.0;
    @SerializedName("taxable")
    @Expose
    private Double taxable;
    @SerializedName("cgsttaxamt")
    @Expose
    private Double cgsttaxamt;
    @SerializedName("sgsttaxamt")
    @Expose
    private Double sgsttaxamt;
    @SerializedName("igsttaxamt")
    @Expose
    private Double igsttaxamt;
    @SerializedName("finalprice")
    @Expose
    private Double finalprice;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("iscoursebenefit")
    @Expose
    private String iscoursebenefit;
    @SerializedName("iscoursedisplaydata")
    @Expose
    private String iscoursedisplaydata;

    @SerializedName("isitemsubdetail")
    @Expose
    private Integer isitemsubdetail;
    @SerializedName("itemsubdetail")
    @Expose
    private ArrayList<Itemsubdetail> itemsubdetail = null;


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

    public String getItemno() {
        return itemno;
    }

    public void setItemno(String itemno) {
        this.itemno = itemno;
    }

    public String getOidid() {
        return oidid;
    }

    public void setOidid(String oidid) {
        this.oidid = oidid;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getDiscountamt() {
        return discountamt;
    }

    public void setDiscountamt(Double discountamt) {
        this.discountamt = discountamt;
    }

    public Double getTaxable() {
        return taxable;
    }

    public void setTaxable(Double taxable) {
        this.taxable = taxable;
    }

    public Double getCgsttaxamt() {
        return cgsttaxamt;
    }

    public void setCgsttaxamt(Double cgsttaxamt) {
        this.cgsttaxamt = cgsttaxamt;
    }

    public Double getSgsttaxamt() {
        return sgsttaxamt;
    }

    public void setSgsttaxamt(Double sgsttaxamt) {
        this.sgsttaxamt = sgsttaxamt;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIscoursebenefit() {
        return iscoursebenefit;
    }

    public void setIscoursebenefit(String iscoursebenefit) {
        this.iscoursebenefit = iscoursebenefit;
    }

    public String getIscoursedisplaydata() {
        return iscoursedisplaydata;
    }

    public void setIscoursedisplaydata(String iscoursedisplaydata) {
        this.iscoursedisplaydata = iscoursedisplaydata;
    }

    public Integer getIsitemsubdetail() {
        return isitemsubdetail;
    }

    public void setIsitemsubdetail(Integer isitemsubdetail) {
        this.isitemsubdetail = isitemsubdetail;
    }

    public ArrayList<Itemsubdetail> getItemsubdetail() {
        return itemsubdetail;
    }

    public void setItemsubdetail(ArrayList<Itemsubdetail> itemsubdetail) {
        this.itemsubdetail = itemsubdetail;
    }


    @Override
    public boolean equals(@Nullable Object obj) {
        Items model = (Items) obj;
        if (this.id.equals(model.getId())) {
            return true;
        } else {
            return false;
        }
    }
}
