package com.example.sane.onlinestore.Models;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblArtistPost {

    @SerializedName("tbl_User")
    @Expose
    private TblUser tblUser;
    @SerializedName("PostId")
    @Expose
    private Integer postId;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("PostDesc")
    @Expose
    private String postDesc;
    @SerializedName("tbl_ArtistPostDetail")
    @Expose
    private List<Object> tblArtistPostDetail = null;

    @SerializedName("tbl_PostNotification")
    @Expose
    private List<Object> tblPostNotification = null;

    /**
     * No args constructor for use in serialization
     */
    public TblArtistPost() {
    }

    public TblArtistPost(TblUser tblUser, Integer postId, Integer userId, String postDesc, List<Object> tblArtistPostDetail, List<Object> tblPostNotification) {
        this.tblUser = tblUser;
        this.postId = postId;
        this.userId = userId;
        this.postDesc = postDesc;
        this.tblArtistPostDetail = tblArtistPostDetail;
        this.tblPostNotification = tblPostNotification;
    }



    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPostDesc() {
        return postDesc;
    }

    public void setPostDesc(String postDesc) {
        this.postDesc = postDesc;
    }

    public TblUser getTblUser() {
        return tblUser;
    }

    public void setTblUser(TblUser tblUser) {
        this.tblUser = tblUser;
    }

    public List<Object> getTblPostNotification() {
        return tblPostNotification;
    }

    public void setTblPostNotification(List<Object> tblPostNotification) {
        this.tblPostNotification = tblPostNotification;
    }

    public List<Object> getTblArtistPostDetail() {
        return tblArtistPostDetail;
    }

    public void setTblArtistPostDetail(List<Object> tblArtistPostDetail) {
        this.tblArtistPostDetail = tblArtistPostDetail;
    }

}



