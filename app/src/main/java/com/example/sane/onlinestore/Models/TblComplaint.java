package com.example.sane.onlinestore.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblComplaint {

    @SerializedName("ComplaintId")
    @Expose
    private Integer complaintId;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("tbl_User")
    @Expose
    private Object tblUser;

    /**
     * No args constructor for use in serialization
     *
     */
    public TblComplaint() {
    }

    /**
     *
     * @param title
     * @param tblUser
     * @param description
     * @param userId
     * @param complaintId
     */
    public TblComplaint(Integer complaintId, String title, String description, Integer userId, Object tblUser) {
        super();
        this.complaintId = complaintId;
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.tblUser = tblUser;
    }

    public Integer getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(Integer complaintId) {
        this.complaintId = complaintId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Object getTblUser() {
        return tblUser;
    }

    public void setTblUser(Object tblUser) {
        this.tblUser = tblUser;
    }

}

