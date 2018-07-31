package com.example.sane.onlinestore.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TblAuctionDetail {

    @SerializedName("AuctionId")
    @Expose
    private Integer auctionId;
    @SerializedName("AuctionDeatilId")
    @Expose
    private Integer auctionDetailId;
    @SerializedName("AuctionImage")
    @Expose
    private String auctionImage;

    public TblAuctionDetail(Integer auctionId, Integer auctionDetailId, String auctionImage) {
        this.auctionId = auctionId;
        this.auctionDetailId = auctionDetailId;
        this.auctionImage = auctionImage;
    }

    public Integer getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Integer auctionId) {
        this.auctionId = auctionId;
    }

    public Integer getAuctionDetailId() {
        return auctionDetailId;
    }

    public void setAuctionDetailId(Integer auctionDetailId) {
        this.auctionDetailId = auctionDetailId;
    }

    public String getAuctionImage() {
        return auctionImage;
    }

    public void setAuctionImage(String auctionImage) {
        this.auctionImage = auctionImage;
    }
}
