package com.app.bookyourplace.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingDetails implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("hotel_id")
    @Expose
    private Integer hotelId;
    @SerializedName("booking_id")
    @Expose
    private String bookingId;
    @SerializedName("booking_startdate")
    @Expose
    private String bookingStartdate;
    @SerializedName("booking_enddate")
    @Expose
    private String bookingEnddate;
    @SerializedName("booking_nights")
    @Expose
    private String bookingNights;
    @SerializedName("booking_cprice")
    @Expose
    private String bookingCprice;
    @SerializedName("booking_adults")
    @Expose
    private String bookingAdults;
    @SerializedName("booking_children")
    @Expose
    private String bookingChildren;
    @SerializedName("booking_rooms")
    @Expose
    private String bookingRooms;
    @SerializedName("booking_hotel_name")
    @Expose
    private String bookingHotelName;
    @SerializedName("booking_hotel_loc")
    @Expose
    private String bookingHotelLoc;
    @SerializedName("booking_hotel_lat")
    @Expose
    private String bookingHotelLat;
    @SerializedName("booking_hotel_long")
    @Expose
    private String bookingHotelLong;
    @SerializedName("booking_hotel_picture")
    @Expose
    private String bookingHotelPicture;
    @SerializedName("booking_username")
    @Expose
    private String bookingUsername;
    @SerializedName("booking_useremail")
    @Expose
    private String bookingUseremail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingStartdate() {
        return bookingStartdate;
    }

    public void setBookingStartdate(String bookingStartdate) {
        this.bookingStartdate = bookingStartdate;
    }

    public String getBookingEnddate() {
        return bookingEnddate;
    }

    public void setBookingEnddate(String bookingEnddate) {
        this.bookingEnddate = bookingEnddate;
    }

    public String getBookingNights() {
        return bookingNights;
    }

    public void setBookingNights(String bookingNights) {
        this.bookingNights = bookingNights;
    }

    public String getBookingCprice() {
        return bookingCprice;
    }

    public void setBookingCprice(String bookingCprice) {
        this.bookingCprice = bookingCprice;
    }

    public String getBookingAdults() {
        return bookingAdults;
    }

    public void setBookingAdults(String bookingAdults) {
        this.bookingAdults = bookingAdults;
    }

    public String getBookingChildren() {
        return bookingChildren;
    }

    public void setBookingChildren(String bookingChildren) {
        this.bookingChildren = bookingChildren;
    }

    public String getBookingRooms() {
        return bookingRooms;
    }

    public void setBookingRooms(String bookingRooms) {
        this.bookingRooms = bookingRooms;
    }

    public String getBookingHotelName() {
        return bookingHotelName;
    }

    public void setBookingHotelName(String bookingHotelName) {
        this.bookingHotelName = bookingHotelName;
    }

    public String getBookingHotelLoc() {
        return bookingHotelLoc;
    }

    public void setBookingHotelLoc(String bookingHotelLoc) {
        this.bookingHotelLoc = bookingHotelLoc;
    }

    public String getBookingHotelLat() {
        return bookingHotelLat;
    }

    public void setBookingHotelLat(String bookingHotelLat) {
        this.bookingHotelLat = bookingHotelLat;
    }

    public String getBookingHotelLong() {
        return bookingHotelLong;
    }

    public void setBookingHotelLong(String bookingHotelLong) {
        this.bookingHotelLong = bookingHotelLong;
    }

    public String getBookingHotelPicture() {
        return bookingHotelPicture;
    }

    public void setBookingHotelPicture(String bookingHotelPicture) {
        this.bookingHotelPicture = bookingHotelPicture;
    }

    public String getBookingUsername() {
        return bookingUsername;
    }

    public void setBookingUsername(String bookingUsername) {
        this.bookingUsername = bookingUsername;
    }

    public String getBookingUseremail() {
        return bookingUseremail;
    }

    public void setBookingUseremail(String bookingUseremail) {
        this.bookingUseremail = bookingUseremail;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.userId);
        dest.writeValue(this.hotelId);
        dest.writeString(this.bookingId);
        dest.writeString(this.bookingStartdate);
        dest.writeString(this.bookingEnddate);
        dest.writeString(this.bookingNights);
        dest.writeString(this.bookingCprice);
        dest.writeString(this.bookingAdults);
        dest.writeString(this.bookingChildren);
        dest.writeString(this.bookingRooms);
        dest.writeString(this.bookingHotelName);
        dest.writeString(this.bookingHotelLoc);
        dest.writeString(this.bookingHotelLat);
        dest.writeString(this.bookingHotelLong);
        dest.writeString(this.bookingHotelPicture);
        dest.writeString(this.bookingUsername);
        dest.writeString(this.bookingUseremail);
    }

    public BookingDetails() {
    }

    protected BookingDetails(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.userId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.hotelId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.bookingId = in.readString();
        this.bookingStartdate = in.readString();
        this.bookingEnddate = in.readString();
        this.bookingNights = in.readString();
        this.bookingCprice = in.readString();
        this.bookingAdults = in.readString();
        this.bookingChildren = in.readString();
        this.bookingRooms = in.readString();
        this.bookingHotelName = in.readString();
        this.bookingHotelLoc = in.readString();
        this.bookingHotelLat = in.readString();
        this.bookingHotelLong = in.readString();
        this.bookingHotelPicture = in.readString();
        this.bookingUsername = in.readString();
        this.bookingUseremail = in.readString();
    }

    public static final Parcelable.Creator<BookingDetails> CREATOR = new Parcelable.Creator<BookingDetails>() {
        @Override
        public BookingDetails createFromParcel(Parcel source) {
            return new BookingDetails(source);
        }

        @Override
        public BookingDetails[] newArray(int size) {
            return new BookingDetails[size];
        }
    };
}
