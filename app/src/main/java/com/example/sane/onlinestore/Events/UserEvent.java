package com.example.sane.onlinestore.Events;

import com.example.sane.onlinestore.Models.TblUser;

import java.util.List;

public class UserEvent {

    private List<TblUser> User;
    private TblUser tblUser;
    private int position;

    public UserEvent(List<TblUser> user, int position) {
        this.User = user;
        this.position = position;

    }
    public UserEvent(TblUser tblUser){
        this.tblUser =tblUser;
    }

    public TblUser getTblUser() {
        return tblUser;
    }

    public void setTblUser(TblUser tblUser) {
        this.tblUser = tblUser;
    }

    public List<TblUser> getUser() {
        return User;
    }

    public void setUser(List<TblUser> user) {
        User = user;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
