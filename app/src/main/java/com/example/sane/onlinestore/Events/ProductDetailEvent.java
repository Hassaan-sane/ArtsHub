package com.example.sane.onlinestore.Events;

import com.example.sane.onlinestore.Models.Item;
import com.example.sane.onlinestore.Models.TblItem;

import java.util.List;

public class ProductDetailEvent {

    private TblItem message;
    private int position;

    public ProductDetailEvent(TblItem message, int position) {
        this.message = message;
        this.position=position;
    }

    public TblItem getMessage() {
        return message;
    }

    public int getPosition() {
        return position;
    }
}
