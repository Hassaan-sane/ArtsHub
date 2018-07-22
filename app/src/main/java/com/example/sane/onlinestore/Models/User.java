package com.example.sane.onlinestore.Models;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("tbl_ArtistPost")
    @Expose
    private List<Object> tblArtistPost = null;
    @SerializedName("tbl_Auction")
    @Expose
    private List<Object> tblAuction = null;
    @SerializedName("tbl_AuctionOrder")
    @Expose
    private List<Object> tblAuctionOrder = null;
    @SerializedName("tbl_Bid")
    @Expose
    private List<Object> tblBid = null;
    @SerializedName("tbl_ItemOrder")
    @Expose
    private List<Object> tblItemOrder = null;
    @SerializedName("tbl_Shipping")
    @Expose
    private List<Object> tblShipping = null;
    @SerializedName("tbl_UserDetail")
    @Expose
    private Object tblUserDetail;
    @SerializedName("tbl_User1")
    @Expose
    private List<Object> tblUser1 = null;
    @SerializedName("tbl_User2")
    @Expose
    private List<Object> tblUser2 = null;
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

    /**
     * No args constructor for use in serialization
     *
     */
    public User() {
    }

    /**
     *
     * @param tblUser2
     * @param tblUser1
     * @param tblUserDetail
     * @param tblAuctionOrder
     * @param aspNetUserId
     * @param tblShipping
     * @param tblAuction
     * @param username
     * @param tblBid
     * @param userId
     * @param name
     * @param tblItemOrder
     * @param role
     * @param tblArtistPost
     */
    public User(List<Object> tblArtistPost, List<Object> tblAuction, List<Object> tblAuctionOrder, List<Object> tblBid, List<Object> tblItemOrder, List<Object> tblShipping, Object tblUserDetail, List<Object> tblUser1, List<Object> tblUser2, Integer userId, String name, String username, String role, String aspNetUserId) {
        super();
        this.tblArtistPost = tblArtistPost;
        this.tblAuction = tblAuction;
        this.tblAuctionOrder = tblAuctionOrder;
        this.tblBid = tblBid;
        this.tblItemOrder = tblItemOrder;
        this.tblShipping = tblShipping;
        this.tblUserDetail = tblUserDetail;
        this.tblUser1 = tblUser1;
        this.tblUser2 = tblUser2;
        this.userId = userId;
        this.name = name;
        this.username = username;
        this.role = role;
        this.aspNetUserId = aspNetUserId;
    }

    public List<Object> getTblArtistPost() {
        return tblArtistPost;
    }

    public void setTblArtistPost(List<Object> tblArtistPost) {
        this.tblArtistPost = tblArtistPost;
    }

    public List<Object> getTblAuction() {
        return tblAuction;
    }

    public void setTblAuction(List<Object> tblAuction) {
        this.tblAuction = tblAuction;
    }

    public List<Object> getTblAuctionOrder() {
        return tblAuctionOrder;
    }

    public void setTblAuctionOrder(List<Object> tblAuctionOrder) {
        this.tblAuctionOrder = tblAuctionOrder;
    }

    public List<Object> getTblBid() {
        return tblBid;
    }

    public void setTblBid(List<Object> tblBid) {
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

    public Object getTblUserDetail() {
        return tblUserDetail;
    }

    public void setTblUserDetail(Object tblUserDetail) {
        this.tblUserDetail = tblUserDetail;
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

}
