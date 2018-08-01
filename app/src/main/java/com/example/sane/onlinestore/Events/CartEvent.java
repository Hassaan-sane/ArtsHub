package com.example.sane.onlinestore.Events;

import com.example.sane.onlinestore.Models.TblCart;

import java.util.List;

public class CartEvent {
    public int message;
    private List<TblCart>  tblCarts;
  private   String AorR;

    public CartEvent(int message, List<TblCart> tblCarts, String aorR) {
        this.message = message;
        this.tblCarts = tblCarts;
        AorR = aorR;
    }

    public String getAorR() {
        return AorR;
    }

    public void setAorR(String aorR) {
        AorR = aorR;
    }

    public List<TblCart> getTblCarts() {
        return tblCarts;
    }

    public void setTblCarts(List<TblCart> tblCarts) {
        this.tblCarts = tblCarts;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }
}
