package com.app.bookyourplace.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hotel implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("hotel_name")
    @Expose
    private String hotelName;
    @SerializedName("hotel_location")
    @Expose
    private String hotelLocation;
    @SerializedName("hotel_lat")
    @Expose
    private String hotelLat;
    @SerializedName("hotel_long")
    @Expose
    private String hotelLong;
    @SerializedName("hotel_price")
    @Expose
    private String hotelPrice;
    @SerializedName("hotel_discount")
    @Expose
    private String hotelDiscount;
    @SerializedName("hotel_rating")
    @Expose
    private String hotelRating;
    @SerializedName("hotel_rooms")
    @Expose
    private String hotelRooms;
    @SerializedName("hotel_images")
    @Expose
    private ImagesH hotelImagesH;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelLocation() {
        return hotelLocation;
    }

    public void setHotelLocation(String hotelLocation) {
        this.hotelLocation = hotelLocation;
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

    public String getHotelPrice() {
        return hotelPrice;
    }

    public void setHotelPrice(String hotelPrice) {
        this.hotelPrice = hotelPrice;
    }

    public String getHotelDiscount() {
        return hotelDiscount;
    }

    public void setHotelDiscount(String hotelDiscount) {
        this.hotelDiscount = hotelDiscount;
    }

    public String getHotelRating() {
        return hotelRating;
    }

    public void setHotelRating(String hotelRating) {
        this.hotelRating = hotelRating;
    }

    public String getHotelRooms() {
        return hotelRooms;
    }

    public void setHotelRooms(String hotelRooms) {
        this.hotelRooms = hotelRooms;
    }

    public ImagesH getHotelImagesH() {
        return hotelImagesH;
    }

    public void setHotelImagesH(ImagesH hotelImagesH) {
        this.hotelImagesH = hotelImagesH;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.hotelName);
        dest.writeString(this.hotelLocation);
        dest.writeString(this.hotelLat);
        dest.writeString(this.hotelLong);
        dest.writeString(this.hotelPrice);
        dest.writeString(this.hotelDiscount);
        dest.writeString(this.hotelRating);
        dest.writeString(this.hotelRooms);
        dest.writeParcelable(this.hotelImagesH, flags);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
    }

    public Hotel() {
    }

    protected Hotel(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.hotelName = in.readString();
        this.hotelLocation = in.readString();
        this.hotelLat = in.readString();
        this.hotelLong = in.readString();
        this.hotelPrice = in.readString();
        this.hotelDiscount = in.readString();
        this.hotelRating = in.readString();
        this.hotelRooms = in.readString();
        this.hotelImagesH = in.readParcelable(ImagesH.class.getClassLoader());
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
    }

    public static final Creator<Hotel> CREATOR = new Creator<Hotel>() {
        @Override
        public Hotel createFromParcel(Parcel source) {
            return new Hotel(source);
        }

        @Override
        public Hotel[] newArray(int size) {
            return new Hotel[size];
        }
    };
}
