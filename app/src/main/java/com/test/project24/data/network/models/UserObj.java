package com.test.project24.data.network.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Salman on 4/10/2018.
 */

public class UserObj {

    @SerializedName("data")
    @Expose
    public User user;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("status")
    @Expose
    public int status;

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
