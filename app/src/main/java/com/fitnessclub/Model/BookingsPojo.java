package com.fitnessclub.Model;

import com.google.gson.annotations.SerializedName;

public class BookingsPojo {

    @SerializedName("name")
    private
    String name;
    @SerializedName("fullname")
    private
    String fullname;
    @SerializedName("dat")
    private
    String dat;
    @SerializedName("message")
    private
    String message;
    @SerializedName("bid")
    private
    String bid;
    @SerializedName("status")
    private
    String status;

    @SerializedName("tim")
    private
    String tim;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDat() {
        return dat;
    }

    public void setDat(String dat) {
        this.dat = dat;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTim() {
        return tim;
    }

    public void setTim(String tim) {
        this.tim = tim;
    }
}
