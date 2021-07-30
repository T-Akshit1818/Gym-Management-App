package com.fitnessclub.Model;

import com.google.gson.annotations.SerializedName;

public class MessagePojo {

    @SerializedName("fromemail")
    private
    String fromemail;
    @SerializedName("toemail")
    private
    String toemail;
    @SerializedName("msg")
    private
    String msg;
    @SerializedName("cid")
    private
    String cid;

    public String getFromemail() {
        return fromemail;
    }

    public void setFromemail(String fromemail) {
        this.fromemail = fromemail;
    }

    public String getToemail() {
        return toemail;
    }

    public void setToemail(String toemail) {
        this.toemail = toemail;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
