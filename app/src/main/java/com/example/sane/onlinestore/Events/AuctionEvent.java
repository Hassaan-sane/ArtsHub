package com.example.sane.onlinestore.Events;

import com.example.sane.onlinestore.Models.TblAuction;

import java.util.ArrayList;
import java.util.List;

public class AuctionEvent {

    private List<TblAuction> message;
    private int position;

    public AuctionEvent(List<TblAuction> message, int position) {
        this.message = message;
        this.position=position;
    }

    public List<TblAuction> getMessage() {
        return message;
    }

    public int getPosition() {
        return position;
    }
}
