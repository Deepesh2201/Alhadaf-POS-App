package com.instanceit.alhadafpos.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attributedetail {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("iconimg")
    @Expose
    private String iconimg;
    @SerializedName("rowdisplayorder")
    @Expose
    private String rowdisplayorder;
    @SerializedName("attributename")
    @Expose
    private String attributename;
    @SerializedName("displayorder")
    @Expose
    private String displayorder;
    @SerializedName("durationname")
    @Expose
    private String durationname;

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

    public String getIconimg() {
        return iconimg;
    }

    public void setIconimg(String iconimg) {
        this.iconimg = iconimg;
    }

    public String getRowdisplayorder() {
        return rowdisplayorder;
    }

    public void setRowdisplayorder(String rowdisplayorder) {
        this.rowdisplayorder = rowdisplayorder;
    }

    public String getAttributename() {
        return attributename;
    }

    public void setAttributename(String attributename) {
        this.attributename = attributename;
    }

    public String getDisplayorder() {
        return displayorder;
    }

    public void setDisplayorder(String displayorder) {
        this.displayorder = displayorder;
    }

    public String getDurationname() {
        return durationname;
    }

    public void setDurationname(String durationname) {
        this.durationname = durationname;
    }
}
