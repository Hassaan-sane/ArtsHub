package com.example.sane.onlinestore.Events;

public class HomeEvent {
    private int message;

    public HomeEvent(int message) {

        this.message = message;
    }

    public int getMessage() {
        return message;
    }
}
