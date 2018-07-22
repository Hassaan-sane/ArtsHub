package com.example.sane.onlinestore.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TblUserDetail {

    @SerializedName("tbl_User")
    @Expose
    private TblUser tbl_User;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("Phone")
    @Expose
    private String phone;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("UserImage")
    @Expose
    private String UserImage;

    public TblUserDetail(TblUser tbl_User, Integer userId, String phone, String email, String address, String userImage) {
        this.tbl_User = tbl_User;
        this.userId = userId;
        this.phone = phone;
        this.email = email;
        this.address = address;
        UserImage = userImage;
    }

    public TblUser getTbl_User() {
        return tbl_User;
    }

    public void setTbl_User(TblUser tbl_User) {
        this.tbl_User = tbl_User;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserImage() {
        return UserImage;
    }

    public void setUserImage(String userImage) {
        UserImage = userImage;
    }
}
