package com.example.sane.onlinestore.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblCart {

    @SerializedName("tbl_Item")
    @Expose
    private TblItem tblItem;
    @SerializedName("tbl_User")
    @Expose
    private TblUser tblUser;
    @SerializedName("tbl_Item_ItemId")
    @Expose
    private Integer tblItemItemId;
    @SerializedName("tbl_User_UserId")
    @Expose
    private Integer tblUserUserId;
    @SerializedName("Quantity")
    @Expose
    private Integer quantity;
    @SerializedName("OrderId")
    @Expose
    private Integer orderId;

    /**
     * No args constructor for use in serialization
     *
     */
    public TblCart() {
    }

    /**
     *
     * @param tblUser
     * @param tblItem
     * @param quantity
     * @param tblItemItemId
     * @param orderId
     * @param tblUserUserId
     */
    public TblCart(TblItem tblItem, TblUser tblUser, Integer tblItemItemId, Integer tblUserUserId, Integer quantity, Integer orderId) {
        super();
        this.tblItem = tblItem;
        this.tblUser = tblUser;
        this.tblItemItemId = tblItemItemId;
        this.tblUserUserId = tblUserUserId;
        this.quantity = quantity;
        this.orderId = orderId;
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

    public Integer getTblItemItemId() {
        return tblItemItemId;
    }

    public void setTblItemItemId(Integer tblItemItemId) {
        this.tblItemItemId = tblItemItemId;
    }

    public Integer getTblUserUserId() {
        return tblUserUserId;
    }

    public void setTblUserUserId(Integer tblUserUserId) {
        this.tblUserUserId = tblUserUserId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

}
