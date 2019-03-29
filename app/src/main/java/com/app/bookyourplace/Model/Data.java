package com.app.bookyourplace.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("user")
    @Expose
    public User user;

    public Data(User user) {
        this.user = user;
    }
}
