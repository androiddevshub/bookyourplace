package com.app.bookyourplace.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImagesP implements Parcelable {

    @SerializedName("img1")
    @Expose
    private String img1;
    @SerializedName("img2")
    @Expose
    private String img2;
    @SerializedName("img3")
    @Expose
    private String img3;
    @SerializedName("img4")
    @Expose
    private String img4;
    @SerializedName("img5")
    @Expose
    private String img5;
    @SerializedName("img6")
    @Expose
    private String img6;
    @SerializedName("img7")
    @Expose
    private String img7;
    @SerializedName("img8")
    @Expose
    private String img8;
    @SerializedName("img9")
    @Expose
    private String img9;
    @SerializedName("img10")
    @Expose
    private String img10;

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getImg4() {
        return img4;
    }

    public void setImg4(String img4) {
        this.img4 = img4;
    }

    public String getImg5() {
        return img5;
    }

    public void setImg5(String img5) {
        this.img5 = img5;
    }

    public String getImg6() {
        return img6;
    }

    public void setImg6(String img6) {
        this.img6 = img6;
    }

    public String getImg7() {
        return img7;
    }

    public void setImg7(String img7) {
        this.img7 = img7;
    }

    public String getImg8() {
        return img8;
    }

    public void setImg8(String img8) {
        this.img8 = img8;
    }

    public String getImg9() {
        return img9;
    }

    public void setImg9(String img9) {
        this.img9 = img9;
    }

    public String getImg10() {
        return img10;
    }

    public void setImg10(String img10) {
        this.img10 = img10;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.img1);
        dest.writeString(this.img2);
        dest.writeString(this.img3);
        dest.writeString(this.img4);
        dest.writeString(this.img5);
        dest.writeString(this.img6);
        dest.writeString(this.img7);
        dest.writeString(this.img8);
        dest.writeString(this.img9);
        dest.writeString(this.img10);
    }

    public ImagesP() {
    }

    protected ImagesP(Parcel in) {
        this.img1 = in.readString();
        this.img2 = in.readString();
        this.img3 = in.readString();
        this.img4 = in.readString();
        this.img5 = in.readString();
        this.img6 = in.readString();
        this.img7 = in.readString();
        this.img8 = in.readString();
        this.img9 = in.readString();
        this.img10 = in.readString();
    }

    public static final Creator<ImagesP> CREATOR = new Creator<ImagesP>() {
        @Override
        public ImagesP createFromParcel(Parcel source) {
            return new ImagesP(source);
        }

        @Override
        public ImagesP[] newArray(int size) {
            return new ImagesP[size];
        }
    };
}
