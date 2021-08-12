package com.fitnessclub.Model;

import com.google.gson.annotations.SerializedName;

public class ComplaintsPojo {


    @SerializedName("tid")
    private
    String tid;
    @SerializedName("type")
    private
    String type;

    @SerializedName("cid")
    private
    String cid;

    @SerializedName("fullname")
    private
    String fullname;

    @SerializedName("email")
    private
    String email;

    @SerializedName("msg")
    private
    String msg;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
