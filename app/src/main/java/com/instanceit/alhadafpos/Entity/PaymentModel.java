package com.instanceit.alhadafpos.Entity;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentModel {
    @SerializedName("type")
    @Expose
    private String type="";
    @SerializedName("paytypeid")
    @Expose
    private String paytypeid;
    @SerializedName("paytypename")
    @Expose
    private String paytypename;
    @SerializedName("payamt")
    @Expose
    private String payamt;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPaytypeid() {
        return paytypeid;
    }

    public void setPaytypeid(String paytypeid) {
        this.paytypeid = paytypeid;
    }

    public String getPaytypename() {
        return paytypename;
    }

    public void setPaytypename(String paytypename) {
        this.paytypename = paytypename;
    }

    public String getPayamt() {
        return payamt;
    }

    public void setPayamt(String payamt) {
        this.payamt = payamt;
    }

    @Override
    public boolean equals(@Nullable Object obj) {

        PaymentModel paymentModel = (PaymentModel) obj;
        if (this.paytypeid.equals(paymentModel.getPaytypeid())) {
            return true;
        } else {
            return false;
        }
    }

}
