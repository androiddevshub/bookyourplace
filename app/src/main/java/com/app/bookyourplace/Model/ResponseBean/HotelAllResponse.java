package com.app.bookyourplace.Model.ResponseBean;

import com.app.bookyourplace.Model.Hotel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class HotelAllResponse {

    @SerializedName("data")
    @Expose
    private ArrayList<Hotel> hotelArrayList = new ArrayList<>();

    public ArrayList<Hotel> getHotelArrayList() {
        return hotelArrayList;
    }
}
