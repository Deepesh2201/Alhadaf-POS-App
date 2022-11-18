package com.instanceit.alhadafpos.Entity;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Model {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("personname")
    @Expose
    private String personname;
    @SerializedName("contact")
    @Expose
    private String contact;
    @SerializedName("iscourse")
    @Expose
    private String iscourse;
    @SerializedName("isonline")
    @Expose
    private String isonline;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("isguest")
    @Expose
    private String isguest;


    public String getIsonline() {
        return isonline;
    }

    public void setIsonline(String isonline) {
        this.isonline = isonline;
    }

    public String getIscourse() {
        return iscourse;
    }

    public void setIscourse(String iscourse) {
        this.iscourse = iscourse;
    }

    public Model() {
    }

    public Model(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getPersonname() {
        return personname;
    }

    public void setPersonname(String personname) {
        this.personname = personname;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(@Nullable Object obj) {

        Model model = (Model) obj;
        if (this.id.equals(model.getId())) {
            return true;
        } else {
            return false;
        }
    }
}
