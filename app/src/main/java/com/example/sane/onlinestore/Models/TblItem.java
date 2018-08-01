package com.example.sane.onlinestore.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblItem{

    @SerializedName("tbl_ItemDetail")
    @Expose
    private List<Object> tblItemDetail = null;
//    @SerializedName("tbl_ItemOrder")
//    @Expose
//    private List<Object> tblItemOrder = null;
//    @SerializedName("tbl_ItemNotification")
//    @Expose
//    private List<Object> tblItemNotification = null;
//    @SerializedName("tbl_Shipping")
//    @Expose
//    private List<Object> tblShipping = null;
    @SerializedName("ItemId")
    @Expose
    private Integer itemId;
    @SerializedName("ItemName")
    @Expose
    private String itemName;
    @SerializedName("ItemDesc")
    @Expose
    private String itemDesc;
    @SerializedName("Price")
    @Expose
    private String price;
    @SerializedName("Discount")
    @Expose
    private String discount;
    @SerializedName("CategoryId")
    @Expose
    private Integer categoryId;
    @SerializedName("Quantity")
    @Expose
    private Integer quantity;

    /**
     * No args constructor for use in serialization
     *
     * @param tblItem
     */
    public TblItem(List<TblItem> tblItem) {
    }

    public TblItem(List<Object> tblItemDetail, Integer itemId, String itemName, String itemDesc, String price, String discount, Integer categoryId, Integer quantity) {
        this.tblItemDetail = tblItemDetail;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemDesc = itemDesc;
        this.price = price;
        this.discount = discount;
        this.categoryId = categoryId;
        this.quantity = quantity;
    }


//    public TblItem(List<Object> tblItemDetail, List<Object> tblItemOrder, List<Object> tblShipping, Integer itemId, String itemName, String itemDesc, String price, String discount, Integer categoryId, Integer quantity) {
//        super();
//        this.tblItemDetail = tblItemDetail;
//        this.tblItemOrder = tblItemOrder;
//        this.tblShipping = tblShipping;
//        this.itemId = itemId;
//        this.itemName = itemName;
//        this.itemDesc = itemDesc;
//        this.price = price;
//        this.discount = discount;
//        this.categoryId = categoryId;
//        this.quantity = quantity;
//    }




    public List<Object> getTblItemDetail() {
        return tblItemDetail;
    }

    public void setTblItemDetail(List<Object> tblItemDetail) {
        this.tblItemDetail = tblItemDetail;
    }

//    public List<Object> getTblItemOrder() {
//        return tblItemOrder;
//    }
//
//    public void setTblItemOrder(List<Object> tblItemOrder) {
//        this.tblItemOrder = tblItemOrder;
//    }
//
//    public List<Object> getTblShipping() {
//        return tblShipping;
//    }
//
//    public void setTblShipping(List<Object> tblShipping) {
//        this.tblShipping = tblShipping;
//    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

//    public List<Object> getTblItemNotification() {
//        return tblItemNotification;
//    }
//
//    public void setTblItemNotification(List<Object> tblItemNotification) {
//        this.tblItemNotification = tblItemNotification;
//    }
}

