package com.app.bookyourplace.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("confirm_password")
    @Expose
    private String password_confirmation;

    @SerializedName("session_id")
    @Expose
    private String session_id;

    @SerializedName("otp")
    @Expose
    private String otp;

    public User(String email) {
        this.email = email;
    }

    public User(String name, String email, String phone, String password, String password_confirmation) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.password_confirmation = password_confirmation;
    }

    public User(String email, String otp, String password, String password_confirmation) {
        this.email = email;
        this.otp = otp;
        this.password = password;
        this.password_confirmation = password_confirmation;
    }

    public User(String email, String otp) {
        this.email = email;
        this.otp = otp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_confirmation() {
        return password_confirmation;
    }

    public void setPassword_confirmation(String password_confirmation) {
        this.password_confirmation = password_confirmation;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
