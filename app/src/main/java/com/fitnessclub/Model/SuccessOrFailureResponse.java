package com.fitnessclub.Model;

import com.google.gson.annotations.SerializedName;

public class SuccessOrFailureResponse {
    @SerializedName("message")
    String message;
    @SerializedName("status")
    String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
