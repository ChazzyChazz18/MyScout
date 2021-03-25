package com.TuScout.SportsMedia.TuScoutApiService;

import com.google.gson.annotations.SerializedName;

public class UploadResponse {

    // variable name should be same as in the json response from php
    @SerializedName("success")
    private boolean success;
    @SerializedName("message")
    private String message;

    public String getMessage() { return message; }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getSuccess() { return success; }

    public void setSuccess(boolean success) {
        this.success = success;
    }

}
