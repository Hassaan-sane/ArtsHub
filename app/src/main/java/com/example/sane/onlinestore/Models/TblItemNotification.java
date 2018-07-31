package com.example.sane.onlinestore.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TblItemNotification {

    @SerializedName("tbl_Item")
    @Expose
    private Item tblItem = null;
    @SerializedName("tbl_User")
    @Expose
    private TblUser tblUser;
    @SerializedName("tbl_User1")
    @Expose
    private TblUser tblUser1;
    @SerializedName("NotificationId")
    @Expose
    private Integer notificationId;
    @SerializedName("BuyerId")
    @Expose
    private Integer buyerId;
    @SerializedName("SellerId")
    @Expose
    private Integer sellerId;
    @SerializedName("ItemId")
    @Expose
    private Integer itemId;
    @SerializedName("ViewTime")
    @Expose
    private String viewTime;

    /**
     * No args constructor for use in serialization
     *
     */

    public TblItemNotification() {
    }
    /**
     *
     * @param notificationId
     * @param tblUser1
     * @param tblUser
     * @param tblItem
     * @param sellerId
     * @param viewTime
     * @param itemId
     * @param buyerId
     */

    public TblItemNotification(Item tblItem, TblUser tblUser, TblUser tblUser1, Integer notificationId, Integer buyerId, Integer sellerId, Integer itemId, String viewTime) {
        this.tblItem = tblItem;
        this.tblUser = tblUser;
        this.tblUser1 = tblUser1;
        this.notificationId = notificationId;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.itemId = itemId;
        this.viewTime = viewTime;
    }


    public Item getTblItem() {
        return tblItem;
    }

    public void setTblItem(Item tblItem) {
        this.tblItem = tblItem;
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

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public Integer getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getViewTime() {
        return viewTime;
    }

    public void setViewTime(String viewTime) {
        this.viewTime = viewTime;
    }
}