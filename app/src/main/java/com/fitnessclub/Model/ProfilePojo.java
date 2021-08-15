package com.fitnessclub.Model;

import com.google.gson.annotations.SerializedName;

public class ProfilePojo {
    @SerializedName("fullname")
    String fullname;
    @SerializedName("email")
    String email;
    @SerializedName("phonenumber")
    String phonenumber;
    @SerializedName("phone")
    private
    String phone;
    @SerializedName("pass")
    String pass;
    @SerializedName("gender")
    String gender;

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

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
