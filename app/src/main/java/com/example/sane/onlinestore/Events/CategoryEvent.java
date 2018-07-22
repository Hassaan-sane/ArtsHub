package com.example.sane.onlinestore.Events;

import com.example.sane.onlinestore.Models.TblCategory;

import java.util.ArrayList;

public class CategoryEvent {

    private ArrayList<TblCategory> message;

    public CategoryEvent(ArrayList<TblCategory> message) {
        this.message = message;
    }

    public ArrayList<TblCategory> getMessage() {
        return message;
    }
}
