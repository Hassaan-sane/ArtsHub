package com.example.sane.onlinestore.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TblFollow {
    @SerializedName("FollowId")
    @Expose
    private Integer followId;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("ArtistId")
    @Expose
    private Integer artistId;
    @SerializedName("tbl_User")
    @Expose
    private TblUser tblUser;
    @SerializedName("tbl_User1")
    @Expose
    private TblUser tblUser1;

    public TblFollow(Integer followId, Integer userId, Integer artistId, TblUser tblUser, TblUser tblUser1) {
        this.followId = followId;
        this.userId = userId;
        this.artistId = artistId;
        this.tblUser = tblUser;
        this.tblUser1 = tblUser1;
    }

    public TblUser getTblUser() {
        return tblUser;
    }

    public void setTblUser(TblUser tblUser) {
        this.tblUser = tblUser;
    }

    public TblUser getTblUser1() {
        return tblUser1;
    }

    public void setTblUser1(TblUser tblUser1) {
        this.tblUser1 = tblUser1;
    }

    public Integer getFollowId() {
        return followId;
    }

    public void setFollowId(Integer followId) {
        this.followId = followId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getArtistId() {
        return artistId;
    }

    public void setArtistId(Integer artistId) {
        this.artistId = artistId;
    }
}
