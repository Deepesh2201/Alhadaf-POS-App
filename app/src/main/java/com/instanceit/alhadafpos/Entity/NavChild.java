package com.instanceit.alhadafpos.Entity;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NavChild {
    @SerializedName("pagename")
    @Expose
    private String pagename;
    @SerializedName("appmenuname")
    @Expose
    private String appmenuname;
    @SerializedName("isindividual")
    @Expose
    private Integer isindividual;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("iconunicode")
    @Expose
    private String iconunicode;
    @SerializedName("iconstyle")
    @Expose
    private String iconstyle;
    @SerializedName("iconclass")
    @Expose
    private String iconclass;
    @SerializedName("viewright")
    @Expose
    private Integer viewright;
    @SerializedName("viewallright")
    @Expose
    private Integer viewallright;
    @SerializedName("viewselfright")
    @Expose
    private Integer viewselfright;
    @SerializedName("addright")
    @Expose
    private Integer addright;
    @SerializedName("addallright")
    @Expose
    private Integer addallright;
    @SerializedName("addselfright")
    @Expose
    private Integer addselfright;
    @SerializedName("editright")
    @Expose
    private Integer editright;
    @SerializedName("editallright")
    @Expose
    private Integer editallright;
    @SerializedName("editselfright")
    @Expose
    private Integer editselfright;
    @SerializedName("delright")
    @Expose
    private Integer delright;
    @SerializedName("delallright")
    @Expose
    private Integer delallright;
    @SerializedName("delselfright")
    @Expose
    private Integer delselfright;
    @SerializedName("printright")
    @Expose
    private Integer printright;
    @SerializedName("printallright")
    @Expose
    private Integer printallright;
    @SerializedName("printselfright")
    @Expose
    private Integer printselfright;
    @SerializedName("changepriceright")
    @Expose
    private Integer changepriceright;

    public String getPagename() {
        return pagename;
    }

    public void setPagename(String pagename) {
        this.pagename = pagename;
    }

    public String getAppmenuname() {
        return appmenuname;
    }

    public void setAppmenuname(String appmenuname) {
        this.appmenuname = appmenuname;
    }

    public Integer getIsindividual() {
        return isindividual;
    }

    public void setIsindividual(Integer isindividual) {
        this.isindividual = isindividual;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIconunicode() {
        return iconunicode;
    }

    public void setIconunicode(String iconunicode) {
        this.iconunicode = iconunicode;
    }

    public String getIconstyle() {
        return iconstyle;
    }

    public void setIconstyle(String iconstyle) {
        this.iconstyle = iconstyle;
    }

    public String getIconclass() {
        return iconclass;
    }

    public void setIconclass(String iconclass) {
        this.iconclass = iconclass;
    }

    public Integer getViewright() {
        return viewright;
    }

    public void setViewright(Integer viewright) {
        this.viewright = viewright;
    }

    public Integer getViewallright() {
        return viewallright;
    }

    public void setViewallright(Integer viewallright) {
        this.viewallright = viewallright;
    }

    public Integer getViewselfright() {
        return viewselfright;
    }

    public void setViewselfright(Integer viewselfright) {
        this.viewselfright = viewselfright;
    }

    public Integer getAddright() {
        return addright;
    }

    public void setAddright(Integer addright) {
        this.addright = addright;
    }

    public Integer getAddallright() {
        return addallright;
    }

    public void setAddallright(Integer addallright) {
        this.addallright = addallright;
    }

    public Integer getAddselfright() {
        return addselfright;
    }

    public void setAddselfright(Integer addselfright) {
        this.addselfright = addselfright;
    }

    public Integer getEditright() {
        return editright;
    }

    public void setEditright(Integer editright) {
        this.editright = editright;
    }

    public Integer getEditallright() {
        return editallright;
    }

    public void setEditallright(Integer editallright) {
        this.editallright = editallright;
    }

    public Integer getEditselfright() {
        return editselfright;
    }

    public void setEditselfright(Integer editselfright) {
        this.editselfright = editselfright;
    }

    public Integer getDelright() {
        return delright;
    }

    public void setDelright(Integer delright) {
        this.delright = delright;
    }

    public Integer getDelallright() {
        return delallright;
    }

    public void setDelallright(Integer delallright) {
        this.delallright = delallright;
    }

    public Integer getDelselfright() {
        return delselfright;
    }

    public void setDelselfright(Integer delselfright) {
        this.delselfright = delselfright;
    }

    public Integer getPrintright() {
        return printright;
    }

    public void setPrintright(Integer printright) {
        this.printright = printright;
    }

    public Integer getPrintallright() {
        return printallright;
    }

    public void setPrintallright(Integer printallright) {
        this.printallright = printallright;
    }

    public Integer getPrintselfright() {
        return printselfright;
    }

    public void setPrintselfright(Integer printselfright) {
        this.printselfright = printselfright;
    }

    public Integer getChangepriceright() {
        return changepriceright;
    }

    public void setChangepriceright(Integer changepriceright) {
        this.changepriceright = changepriceright;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        NavChild navChild = (NavChild) obj;
        if (this.appmenuname.equals(navChild.getAppmenuname())) {
            return true;
        } else {
            return false;
        }
    }

    @NonNull
    @Override
    public String toString() {
        return appmenuname;
    }
}
