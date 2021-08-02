package com.fitnessclub.Model;

import com.google.gson.annotations.SerializedName;

public class WotoutPojo {

    //SELECT `tid`, `type`, `level`, `tim`, `gender`, `about`, `image`, `trainer_email` FROM `training` WHERE 1

    @SerializedName("tid")
    private
    String tid;
    @SerializedName("type")
    private
    String type;
    @SerializedName("level")
    private
    String level;
    @SerializedName("tim")
    private
    String tim;
    @SerializedName("gender")
    private
    String gender;
    @SerializedName("about")
    private
    String about;
    @SerializedName("image")
    private
    String image;
    @SerializedName("trainer_email")
    private
    String trainer_email;

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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTim() {
        return tim;
    }

    public void setTim(String tim) {
        this.tim = tim;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTrainer_email() {
        return trainer_email;
    }

    public void setTrainer_email(String trainer_email) {
        this.trainer_email = trainer_email;
    }
}
