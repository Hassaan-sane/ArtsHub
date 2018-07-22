package com.example.sane.onlinestore.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblShipping {

    @SerializedName("tbl_Item")
    @Expose
    private TblItem tblItem;
    @SerializedName("tbl_User")
    @Expose
    private TblUser tblUser;

    @SerializedName("ShippingId")
    @Expose
    private Integer shippingId;
    @SerializedName("ShippingDate")
    @Expose
    private String shippingDate;
    @SerializedName("ShippingTotal")
    @Expose
    private String shippingTotal;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("ItemId")
    @Expose
    private Integer itemId;
    @SerializedName("Quantity")
    @Expose
    private Integer quantity;

    /**
     * No args constructor for use in serialization
     *
     */
    public TblShipping() {
    }

    /**
     *
     * @param shippingId
     * @param userId
     * @param shippingTotal
     * @param quantity
     * @param itemId
     * @param shippingDate
     */

    public TblShipping(TblItem tblItem, TblUser tblUser, Integer shippingId, String shippingDate, String shippingTotal, Integer userId, Integer itemId, Integer quantity) {
        this.tblItem = tblItem;
        this.tblUser = tblUser;
        this.shippingId = shippingId;
        this.shippingDate = shippingDate;
        this.shippingTotal = shippingTotal;
        this.userId = userId;
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public TblItem getTblItem() {
        return tblItem;
    }

    public void setTblItem(TblItem tblItem) {
        this.tblItem = tblItem;
    }

    public TblUser getTblUser() {
        return tblUser;
    }

    public void setTblUser(TblUser tblUser) {
        this.tblUser = tblUser;
    }

    public Integer getShippingId() {
        return shippingId;
    }

    public void setShippingId(Integer shippingId) {
        this.shippingId = shippingId;
    }

    public String getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(String shippingDate) {
        this.shippingDate = shippingDate;
    }

    public String getShippingTotal() {
        return shippingTotal;
    }

    public void setShippingTotal(String shippingTotal) {
        this.shippingTotal = shippingTotal;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}