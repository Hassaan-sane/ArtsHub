package com.example.sane.onlinestore.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblUser {

    @SerializedName("tbl_ArtistPost")
    @Expose
    private List<TblArtistPost> tblArtistPost = null;
    @SerializedName("tbl_Auction")
    @Expose
    private List<TblAuction> tblAuction = null;
    @SerializedName("tbl_AuctionOrder")
    @Expose
    private List<Object> tblAuctionOrder = null;
    @SerializedName("tbl_Bid")
    @Expose
    private List<TblBid> tblBid = null;
    @SerializedName("tbl_ItemOrder")
    @Expose
    private List<Object> tblItemOrder = null;
    @SerializedName("tbl_Shipping")
    @Expose
    private List<Object> tblShipping = null;
    @SerializedName("tbl_User1")
    @Expose
    private List<Object> tblUser1 = null;
    @SerializedName("tbl_User2")
    @Expose
    private List<Object> tblUser2 = null;
    @SerializedName("tbl_UserDetail")
    @Expose
    private TblUserDetail tblUserDetail;
    @SerializedName("tbl_PostNotification")
    @Expose
    private List<Object> tblPostNotification = null;
    @SerializedName("tbl_PostNotification1")
    @Expose
    private List<Object> tblPostNotification1 = null;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Username")
    @Expose
    private String username;
    @SerializedName("Role")
    @Expose
    private String role;
    @SerializedName("AspNetUserId")
    @Expose
    private String aspNetUserId;
    @SerializedName("IsActive")
    @Expose
    private String isActive;




    public TblUser() {
    }

    public TblUser(List<TblArtistPost> tblArtistPost, List<TblAuction> tblAuction, List<Object> tblAuctionOrder, List<TblBid> tblBid, List<Object> tblItemOrder, List<Object> tblShipping, List<Object> tblUser1, List<Object> tblUser2, TblUserDetail tblUserDetail, List<Object> tblPostNotification, List<Object> tblPostNotification1, Integer userId, String name, String username, String role, String aspNetUserId, String isActive) {
        this.tblArtistPost = tblArtistPost;
        this.tblAuction = tblAuction;
        this.tblAuctionOrder = tblAuctionOrder;
        this.tblBid = tblBid;
        this.tblItemOrder = tblItemOrder;
        this.tblShipping = tblShipping;
        this.tblUser1 = tblUser1;
        this.tblUser2 = tblUser2;
        this.tblUserDetail = tblUserDetail;
        this.tblPostNotification = tblPostNotification;
        this.tblPostNotification1 = tblPostNotification1;
        this.userId = userId;
        this.name = name;
        this.username = username;
        this.role = role;
        this.aspNetUserId = aspNetUserId;
        this.isActive = isActive;
    }

    public List<Object> getTblPostNotification() {
        return tblPostNotification;
    }

    public void setTblPostNotification(List<Object> tblPostNotification) {
        this.tblPostNotification = tblPostNotification;
    }

    public List<Object> getTblPostNotification1() {
        return tblPostNotification1;
    }

    public void setTblPostNotification1(List<Object> tblPostNotification1) {
        this.tblPostNotification1 = tblPostNotification1;
    }

    public TblUserDetail getTblUserDetail() {
        return tblUserDetail;
    }

    public void setTblUserDetail(TblUserDetail tblUserDetail) {
        this.tblUserDetail = tblUserDetail;
    }

    public List<TblArtistPost> getTblArtistPost() {
        return tblArtistPost;
    }

    public void setTblArtistPost(List<TblArtistPost> tblArtistPost) {
        this.tblArtistPost = tblArtistPost;
    }

    public List<TblAuction> getTblAuction() {
        return tblAuction;
    }

    public void setTblAuction(List<TblAuction> tblAuction) {
        this.tblAuction = tblAuction;
    }

    public List<Object> getTblAuctionOrder() {
        return tblAuctionOrder;
    }

    public void setTblAuctionOrder(List<Object> tblAuctionOrder) {
        this.tblAuctionOrder = tblAuctionOrder;
    }

    public List<TblBid> getTblBid() {
        return tblBid;
    }

    public void setTblBid(List<TblBid> tblBid) {
        this.tblBid = tblBid;
    }

    public List<Object> getTblItemOrder() {
        return tblItemOrder;
    }

    public void setTblItemOrder(List<Object> tblItemOrder) {
        this.tblItemOrder = tblItemOrder;
    }

    public List<Object> getTblShipping() {
        return tblShipping;
    }

    public void setTblShipping(List<Object> tblShipping) {
        this.tblShipping = tblShipping;
    }

    public List<Object> getTblUser1() {
        return tblUser1;
    }

    public void setTblUser1(List<Object> tblUser1) {
        this.tblUser1 = tblUser1;
    }

    public List<Object> getTblUser2() {
        return tblUser2;
    }

    public void setTblUser2(List<Object> tblUser2) {
        this.tblUser2 = tblUser2;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAspNetUserId() {
        return aspNetUserId;
    }

    public void setAspNetUserId(String aspNetUserId) {
        this.aspNetUserId = aspNetUserId;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}