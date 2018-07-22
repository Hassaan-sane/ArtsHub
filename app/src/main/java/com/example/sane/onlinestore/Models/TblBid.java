package com.example.sane.onlinestore.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblBid {

    @SerializedName("tbl_Auction")
    @Expose
    private TblAuction tblAuction;
    @SerializedName("tbl_User")
    @Expose
    private TblUser tblUser;
    @SerializedName("AuctionItemId")
    @Expose
    private Integer auctionItemId;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("BidPrice")
    @Expose
    private String bidPrice;

    /**
     * No args constructor for use in serialization
     *
     */
    public TblBid() {
    }

    /**
     *
     * @param bidPrice
     * @param tblUser
     * @param userId
     * @param tblAuction
     * @param auctionItemId
     */
    public TblBid(TblAuction tblAuction, TblUser tblUser, Integer auctionItemId, Integer userId, String bidPrice) {
        super();
        this.tblAuction = tblAuction;
        this.tblUser = tblUser;
        this.auctionItemId = auctionItemId;
        this.userId = userId;
        this.bidPrice = bidPrice;
    }

    public TblAuction getTblAuction() {
        return tblAuction;
    }

    public void setTblAuction(TblAuction tblAuction) {
        this.tblAuction = tblAuction;
    }

    public TblUser getTblUser() {
        return tblUser;
    }

    public void setTblUser(TblUser tblUser) {
        this.tblUser = tblUser;
    }

    public Integer getAuctionItemId() {
        return auctionItemId;
    }

    public void setAuctionItemId(Integer auctionItemId) {
        this.auctionItemId = auctionItemId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getBidPrice() {
        return bidPrice;
    }

    public void setBidPrice(String bidPrice) {
        this.bidPrice = bidPrice;
    }

}