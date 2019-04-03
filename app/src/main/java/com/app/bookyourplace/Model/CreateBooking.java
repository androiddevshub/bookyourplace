package com.app.bookyourplace.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateBooking implements Parcelable {

    @SerializedName("hotel_id")
    @Expose
    private String hotelId;
    @SerializedName("booking_id")
    @Expose
    private String bookingId;
    @SerializedName("startdate")
    @Expose
    private String startdate;
    @SerializedName("enddate")
    @Expose
    private String enddate;
    @SerializedName("nights")
    @Expose
    private String nights;
    @SerializedName("cprice")
    @Expose
    private String cprice;
    @SerializedName("adults")
    @Expose
    private String adults;
    @SerializedName("chlidren")
    @Expose
    private String chlidren;
    @SerializedName("rooms")
    @Expose
    private String rooms;
    @SerializedName("hotel_name")
    @Expose
    private String hotelName;
    @SerializedName("hotel_loc")
    @Expose
    private String hotelLoc;
    @SerializedName("hotel_lat")
    @Expose
    private String hotelLat;
    @SerializedName("hotel_long")
    @Expose
    private String hotelLong;
    @SerializedName("hotel_picture")
    @Expose
    private String hotelPicture;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;

    public CreateBooking(String hotelId, String bookingId, String startdate, String enddate, String nights, String cprice, String adults, String chlidren, String rooms, String hotelName, String hotelLoc, String hotelLat, String hotelLong, String hotelPicture, String name, String email) {
        this.hotelId = hotelId;
        this.bookingId = bookingId;
        this.startdate = startdate;
        this.enddate = enddate;
        this.nights = nights;
        this.cprice = cprice;
        this.adults = adults;
        this.chlidren = chlidren;
        this.rooms = rooms;
        this.hotelName = hotelName;
        this.hotelLoc = hotelLoc;
        this.hotelLat = hotelLat;
        this.hotelLong = hotelLong;
        this.hotelPicture = hotelPicture;
        this.name = name;
        this.email = email;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getNights() {
        return nights;
    }

    public void setNights(String nights) {
        this.nights = nights;
    }

    public String getCprice() {
        return cprice;
    }

    public void setCprice(String cprice) {
        this.cprice = cprice;
    }

    public String getAdults() {
        return adults;
    }

    public void setAdults(String adults) {
        this.adults = adults;
    }

    public String getChlidren() {
        return chlidren;
    }

    public void setChlidren(String chlidren) {
        this.chlidren = chlidren;
    }

    public String getRooms() {
        return rooms;
    }

    public void setRooms(String rooms) {
        this.rooms = rooms;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelLoc() {
        return hotelLoc;
    }

    public void setHotelLoc(String hotelLoc) {
        this.hotelLoc = hotelLoc;
    }

    public String getHotelLat() {
        return hotelLat;
    }

    public void setHotelLat(String hotelLat) {
        this.hotelLat = hotelLat;
    }

    public String getHotelLong() {
        return hotelLong;
    }

    public void setHotelLong(String hotelLong) {
        this.hotelLong = hotelLong;
    }

    public String getHotelPicture() {
        return hotelPicture;
    }

    public void setHotelPicture(String hotelPicture) {
        this.hotelPicture = hotelPicture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.hotelId);
        dest.writeString(this.bookingId);
        dest.writeString(this.startdate);
        dest.writeString(this.enddate);
        dest.writeString(this.nights);
        dest.writeString(this.cprice);
        dest.writeString(this.adults);
        dest.writeString(this.chlidren);
        dest.writeString(this.rooms);
        dest.writeString(this.hotelName);
        dest.writeString(this.hotelLoc);
        dest.writeString(this.hotelLat);
        dest.writeString(this.hotelLong);
        dest.writeString(this.hotelPicture);
        dest.writeString(this.name);
        dest.writeString(this.email);
    }

    protected CreateBooking(Parcel in) {
        this.hotelId = in.readString();
        this.bookingId = in.readString();
        this.startdate = in.readString();
        this.enddate = in.readString();
        this.nights = in.readString();
        this.cprice = in.readString();
        this.adults = in.readString();
        this.chlidren = in.readString();
        this.rooms = in.readString();
        this.hotelName = in.readString();
        this.hotelLoc = in.readString();
        this.hotelLat = in.readString();
        this.hotelLong = in.readString();
        this.hotelPicture = in.readString();
        this.name = in.readString();
        this.email = in.readString();
    }

    public static final Parcelable.Creator<CreateBooking> CREATOR = new Parcelable.Creator<CreateBooking>() {
        @Override
        public CreateBooking createFromParcel(Parcel source) {
            return new CreateBooking(source);
        }

        @Override
        public CreateBooking[] newArray(int size) {
            return new CreateBooking[size];
        }
    };
}
