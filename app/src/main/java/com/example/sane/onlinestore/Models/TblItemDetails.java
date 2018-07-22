package com.example.sane.onlinestore.Models;

public class TblItemDetails {

TblItem tblItem;
    int ItemId;
    int ItemDetailId;
    byte[] Image;

    public TblItem getTblItem() {
        return tblItem;
    }

    public void setTblItem(TblItem tblItem) {
        this.tblItem = tblItem;
    }

    public int getItemId() {
        return ItemId;
    }

    public void setItemId(int itemId) {
        ItemId = itemId;
    }

    public int getItemDetailId() {
        return ItemDetailId;
    }

    public void setItemDetailId(int itemDetailId) {
        ItemDetailId = itemDetailId;
    }

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] image) {
        Image = image;
    }

    public TblItemDetails(TblItem tblItem, int itemId, int itemDetailId, byte[] image) {
        this.tblItem = tblItem;
        ItemId = itemId;
        ItemDetailId = itemDetailId;
        Image = image;
    }
}
