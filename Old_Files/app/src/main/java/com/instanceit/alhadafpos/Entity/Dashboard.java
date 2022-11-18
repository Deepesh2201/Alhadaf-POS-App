package com.instanceit.alhadafpos.Entity;


import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.michaelrocks.paranoid.Obfuscate;

@Obfuscate
public class Dashboard {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("uniqname")
    @Expose
    private String uniqname;
    @SerializedName("dashviewright")
    @Expose
    private Integer dashviewright;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUniqname() {
        return uniqname;
    }

    public void setUniqname(String uniqname) {
        this.uniqname = uniqname;
    }

    public Integer getDashviewright() {
        return dashviewright;
    }

    public void setDashviewright(Integer dashviewright) {
        this.dashviewright = dashviewright;
    }



    @Override
    public boolean equals(@Nullable Object obj) {
        Dashboard userright = (Dashboard) obj;
        if (this.uniqname.equals(userright.getUniqname())) {
            return true;
        } else {
            return false;
        }
    }
}
