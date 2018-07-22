package com.example.sane.onlinestore.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblAuction {

    @SerializedName("tbl_User")
    @Expose
    private TblUser tblUser;
    @SerializedName("tbl_AuctionDetial")
    @Expose
    private List<Object> tblAuctionDetial = null;
    @SerializedName("tbl_AuctionOrder")
    @Expose
    private List<Object> tblAuctionOrder = null;
    @SerializedName("tbl_Bid")
    @Expose
    private List<TblBid> tblBid = null;
    @SerializedName("AuctionId")
    @Expose
    private Integer auctionId;
    @SerializedName("AuctionName")
    @Expose
    private String auctionName;
    @SerializedName("AuctionLastDate")
    @Expose
    private String auctionLastDate;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("AuctionDesc")
    @Expose
    private String auctionDesc;
    @SerializedName("StartingBid")
    @Expose
    private String startingBid;
    @SerializedName("AuctionStartDate")
    @Expose
    private String auctionStartDate;

    /**
     * No args constructor for use in serialization
     *
     */
    public TblAuction() {
    }

    public TblAuction(TblUser tblUser, List<Object> tblAuctionDetial, List<Object> tblAuctionOrder, List<TblBid> tblBid, Integer auctionId, String auctionName, String auctionLastDate, Integer userId, String auctionDesc, String startingBid, String auctionStartDate) {
        this.tblUser = tblUser;
        this.tblAuctionDetial = tblAuctionDetial;
        this.tblAuctionOrder = tblAuctionOrder;
        this.tblBid = tblBid;
        this.auctionId = auctionId;
        this.auctionName = auctionName;
        this.auctionLastDate = auctionLastDate;
        this.userId = userId;
        this.auctionDesc = auctionDesc;
        this.startingBid = startingBid;
        this.auctionStartDate = auctionStartDate;
    }




    public TblUser getTblUser() {
        return tblUser;
    }

    public void setTblUser(TblUser tblUser) {
        this.tblUser = tblUser;
    }

    public List<Object> getTblAuctionDetial() {
        return tblAuctionDetial;
    }

    public void setTblAuctionDetial(List<Object> tblAuctionDetial) {
        this.tblAuctionDetial = tblAuctionDetial;
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

    public Integer getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Integer auctionId) {
        this.auctionId = auctionId;
    }

    public String getAuctionName() {
        return auctionName;
    }

    public void setAuctionName(String auctionName) {
        this.auctionName = auctionName;
    }

    public String getAuctionLastDate() {
        return auctionLastDate;
    }

    public void setAuctionLastDate(String auctionLastDate) {
        this.auctionLastDate = auctionLastDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAuctionDesc() {
        return auctionDesc;
    }

    public void setAuctionDesc(String auctionDesc) {
        this.auctionDesc = auctionDesc;
    }

    public String getStartingBid() {
        return startingBid;
    }

    public void setStartingBid(String startingBid) {
        this.startingBid = startingBid;
    }

    public String getAuctionStartDate() {
        return auctionStartDate;
    }

    public void setAuctionStartDate(String auctionStartDate) {
        this.auctionStartDate = auctionStartDate;
    }
}


