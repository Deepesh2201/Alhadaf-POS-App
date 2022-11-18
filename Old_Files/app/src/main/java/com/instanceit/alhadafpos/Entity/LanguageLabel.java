package com.instanceit.alhadafpos.Entity;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.michaelrocks.paranoid.Obfuscate;

@Obfuscate
public class LanguageLabel {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("labelnameid")
    @Expose
    public String labelnameid;
    @SerializedName("label")
    @Expose
    public String label;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabelnameid() {
        return labelnameid;
    }

    public void setLabelnameid(String labelnameid) {
        this.labelnameid = labelnameid;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        LanguageLabel userright = (LanguageLabel) obj;
        if (this.labelnameid.equals(userright.getLabelnameid())) {
            return true;
        } else {
            return false;
        }
    }

}
