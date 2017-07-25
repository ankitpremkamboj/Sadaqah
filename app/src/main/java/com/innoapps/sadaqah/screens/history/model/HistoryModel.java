package com.innoapps.sadaqah.screens.history.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ankit on 7/3/2017.
 */

public class HistoryModel {
    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private ArrayList<Datum> data = null;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Datum> getData() {
        return data;
    }

    public void setData(ArrayList<Datum> data) {
        this.data = data;
    }


    public class Datum {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("donation_id")
        @Expose
        private String donationId;
        @SerializedName("donation_name")
        @Expose
        private String donationName;
        @SerializedName("provider")
        @Expose
        private String provider;
        @SerializedName("donation_type")
        @Expose
        private String donationType;
        @SerializedName("brand_logo")
        @Expose
        private String brandLogo;
        @SerializedName("amount")
        @Expose
        private String amount;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDonationId() {
            return donationId;
        }

        public void setDonationId(String donationId) {
            this.donationId = donationId;
        }

        public String getDonationName() {
            return donationName;
        }

        public void setDonationName(String donationName) {
            this.donationName = donationName;
        }

        public String getProvider() {
            return provider;
        }

        public void setProvider(String provider) {
            this.provider = provider;
        }

        public String getDonationType() {
            return donationType;
        }

        public void setDonationType(String donationType) {
            this.donationType = donationType;
        }

        public String getBrandLogo() {
            return brandLogo;
        }

        public void setBrandLogo(String brandLogo) {
            this.brandLogo = brandLogo;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

    }
}
