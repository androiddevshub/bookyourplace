package com.app.bookyourplace.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Place implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("place_name")
    @Expose
    private String placeName;
    @SerializedName("place_images")
    @Expose
    private ImagesP placeImagesP;
    @SerializedName("place_location")
    @Expose
    private String placeLocation;
    @SerializedName("place_lat")
    @Expose
    private String placeLat;
    @SerializedName("place_long")
    @Expose
    private String placeLong;
    @SerializedName("place_description")
    @Expose
    private String placeDescription;
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

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public ImagesP getPlaceImagesP() {
        return placeImagesP;
    }

    public void setPlaceImagesH(ImagesP placeImagesP) {
        this.placeImagesP = placeImagesP;
    }

    public String getPlaceLocation() {
        return placeLocation;
    }

    public void setPlaceLocation(String placeLocation) {
        this.placeLocation = placeLocation;
    }

    public String getPlaceLat() {
        return placeLat;
    }

    public void setPlaceLat(String placeLat) {
        this.placeLat = placeLat;
    }

    public String getPlaceLong() {
        return placeLong;
    }

    public void setPlaceLong(String placeLong) {
        this.placeLong = placeLong;
    }

    public String getPlaceDescription() {
        return placeDescription;
    }

    public void setPlaceDescription(String placeDescription) {
        this.placeDescription = placeDescription;
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
        dest.writeString(this.placeName);
        dest.writeParcelable(this.placeImagesP, flags);
        dest.writeString(this.placeLocation);
        dest.writeString(this.placeLat);
        dest.writeString(this.placeLong);
        dest.writeString(this.placeDescription);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
    }

    public Place() {
    }

    protected Place(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.placeName = in.readString();
        this.placeImagesP = in.readParcelable(ImagesH.class.getClassLoader());
        this.placeLocation = in.readString();
        this.placeLat = in.readString();
        this.placeLong = in.readString();
        this.placeDescription = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
    }

    public static final Parcelable.Creator<Place> CREATOR = new Parcelable.Creator<Place>() {
        @Override
        public Place createFromParcel(Parcel source) {
            return new Place(source);
        }

        @Override
        public Place[] newArray(int size) {
            return new Place[size];
        }
    };
}
