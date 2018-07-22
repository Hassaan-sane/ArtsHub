package com.example.sane.onlinestore.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblPostNotification {
    @SerializedName("tbl_ArtistPost")
    @Expose
    private TblArtistPost tblArtistPost;
    @SerializedName("tbl_User")
    @Expose
    private TblUser tblUser;
    @SerializedName("tbl_User1")
    @Expose
    private TblUser tblUser1;
    @SerializedName("ArtistId")
    @Expose
    private Integer artistId;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("PostId")
    @Expose
    private Integer postId;
    @SerializedName("ViewTime")
    @Expose
    private String viewTime;
    @SerializedName("NotificationId")
    @Expose
    private Integer notificationId;


    public TblPostNotification(TblArtistPost tblArtistPost, TblUser tblUser, TblUser tblUser1, Integer artistId, Integer userId, Integer postId, String viewTime, Integer notificationId) {
        this.tblArtistPost = tblArtistPost;
        this.tblUser = tblUser;
        this.tblUser1 = tblUser1;
        this.artistId = artistId;
        this.userId = userId;
        this.postId = postId;
        this.viewTime = viewTime;
        this.notificationId = notificationId;
    }

    public TblArtistPost getTblArtistPost() {
        return tblArtistPost;
    }

    public void setTblArtistPost(TblArtistPost tblArtistPost) {
        this.tblArtistPost = tblArtistPost;
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

    public Integer getArtistId() {
        return artistId;
    }

    public void setArtistId(Integer artistId) {
        this.artistId = artistId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getViewTime() {
        return viewTime;
    }

    public void setViewTime(String viewTime) {
        this.viewTime = viewTime;
    }

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }
}
