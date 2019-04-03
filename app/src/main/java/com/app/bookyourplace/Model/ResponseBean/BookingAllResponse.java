package com.app.bookyourplace.Model.ResponseBean;

import com.app.bookyourplace.Model.BookingDetails;
import com.app.bookyourplace.Model.Hotel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BookingAllResponse {

    @SerializedName("data")
    @Expose
    private ArrayList<BookingDetails> bookingDetailsArrayList = new ArrayList<>();

    public ArrayList<BookingDetails> getBookingDetailsArrayList() {
        return bookingDetailsArrayList;
    }

    public void setBookingDetailsArrayList(ArrayList<BookingDetails> bookingDetailsArrayList) {
        this.bookingDetailsArrayList = bookingDetailsArrayList;
    }
}
