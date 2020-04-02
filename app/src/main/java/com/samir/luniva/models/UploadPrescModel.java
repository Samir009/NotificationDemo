package com.samir.luniva.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * created by SAMIR SHRESTHA on 2/16/2020  at 1:01 PM
 */

public class UploadPrescModel {

    @SerializedName("UserId")
    @Expose
    private Integer userId;

    @SerializedName("Title")
    @Expose
    private String title;

    @SerializedName("Comment")
    @Expose
    private String comment;

    @SerializedName("CreatedOn")
    @Expose
    private String createdOn;

    @SerializedName("File")
    @Expose
    private Object isActive;
}
