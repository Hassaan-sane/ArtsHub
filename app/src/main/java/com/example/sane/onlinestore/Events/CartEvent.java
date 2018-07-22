package com.example.sane.onlinestore.Events;

public class CartEvent {
    public int message;

    public CartEvent(int message) {
        this.message=message;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }
}
