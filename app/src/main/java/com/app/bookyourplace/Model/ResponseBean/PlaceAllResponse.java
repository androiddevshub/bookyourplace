package com.app.bookyourplace.Model.ResponseBean;

import com.app.bookyourplace.Model.Hotel;
import com.app.bookyourplace.Model.Place;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PlaceAllResponse {

    @SerializedName("data")
    @Expose
    private ArrayList<Place> placeArrayList = new ArrayList<>();

    public ArrayList<Place> getHotelArrayList() {
        return placeArrayList;
    }
}
