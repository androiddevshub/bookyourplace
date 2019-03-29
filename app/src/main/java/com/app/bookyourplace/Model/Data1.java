package com.app.bookyourplace.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data1 {

    @SerializedName("user")
    @Expose
    public Login login;

    public Data1(Login login) {
        this.login = login;
    }
}
