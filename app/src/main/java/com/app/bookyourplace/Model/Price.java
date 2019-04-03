package com.app.bookyourplace.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Price {

    @SerializedName("nights")
    @Expose
    private int nights;

    @SerializedName("rooms")
    @Expose
    private int rooms;

    @SerializedName("adults")
    @Expose
    private int adults;

    @SerializedName("children")
    @Expose
    private int children;

    @SerializedName("id")
    @Expose
    private int id;

    public Price(int nights, int rooms, int adults, int children, int id) {
        this.nights = nights;
        this.rooms = rooms;
        this.adults = adults;
        this.children = children;
        this.id = id;
    }
}
