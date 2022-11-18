package com.instanceit.alhadafpos.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.michaelrocks.paranoid.Obfuscate;

@Obfuscate
public class Language {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("languagename")
    @Expose
    public String languagename;
    @SerializedName("languageengname")
    @Expose
    public String languageengname;
    @SerializedName("icon")
    @Expose
    public String icon;
    @SerializedName("label1")
    @Expose
    public String label1;
    @SerializedName("label2")
    @Expose
    public String label2;
    @SerializedName("label3")
    @Expose
    public String label3;


    public boolean checked_position;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLanguagename() {
        return languagename;
    }

    public void setLanguagename(String languagename) {
        this.languagename = languagename;
    }

    public String getLanguageengname() {
        return languageengname;
    }

    public void setLanguageengname(String languageengname) {
        this.languageengname = languageengname;
    }

    public boolean isChecked_position() {
        return checked_position;
    }

    public void setChecked_position(boolean checked_position) {
        this.checked_position = checked_position;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLabel1() {
        return label1;
    }

    public void setLabel1(String label1) {
        this.label1 = label1;
    }

    public String getLabel2() {
        return label2;
    }

    public void setLabel2(String label2) {
        this.label2 = label2;
    }

    public String getLabel3() {
        return label3;
    }

    public void setLabel3(String label3) {
        this.label3 = label3;
    }

    @Override
    public boolean equals(Object o) {

        Language languageModel = (Language) o;
        if (this.id.equals(languageModel.getId())) {
            return true;
        } else {
            return false;
        }
    }
}
