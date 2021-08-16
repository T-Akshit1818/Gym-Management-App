package com.fitnessclub.Model;

import com.google.gson.annotations.SerializedName;

public class ProgressPojo {
    @SerializedName("pid")
    private
    String pid;
    @SerializedName("email")
    private
    String email;
    @SerializedName("dat")
    private
    String dat;
    @SerializedName("intime")
    private
    String intime;
    @SerializedName("outtime")
    private
    String outtime;
    @SerializedName("tot")
    private
    String tot;
    @SerializedName("type")
    private
    String type;
    @SerializedName("cal")
    private
    String cal;
    @SerializedName("score")
    private
    String score;
    @SerializedName("status")
    private
    String status;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDat() {
        return dat;
    }

    public void setDat(String dat) {
        this.dat = dat;
    }

    public String getIntime() {
        return intime;
    }

    public void setIntime(String intime) {
        this.intime = intime;
    }

    public String getOuttime() {
        return outtime;
    }

    public void setOuttime(String outtime) {
        this.outtime = outtime;
    }

    public String getTot() {
        return tot;
    }

    public void setTot(String tot) {
        this.tot = tot;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCal() {
        return cal;
    }

    public void setCal(String cal) {
        this.cal = cal;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
