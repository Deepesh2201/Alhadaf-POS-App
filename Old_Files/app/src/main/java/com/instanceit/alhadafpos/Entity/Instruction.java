package com.instanceit.alhadafpos.Entity;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Instruction {

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

    private Integer isitemadded = 0;

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

    public Integer getIsitemadded() {
        return isitemadded;
    }

    public void setIsitemadded(Integer isitemadded) {
        this.isitemadded = isitemadded;
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

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(@Nullable Object obj) {

        Instruction model = (Instruction) obj;
        if (this.id.equals(model.getId())) {
            return true;
        } else {
            return false;
        }
    }
}
