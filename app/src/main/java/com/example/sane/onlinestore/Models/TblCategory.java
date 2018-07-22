package com.example.sane.onlinestore.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblCategory {

    @SerializedName("tbl_Item")
    @Expose
    private List<TblItem> tblItem = null;
    @SerializedName("CategoryId")
    @Expose
    private Integer categoryId;
    @SerializedName("CategoryName")
    @Expose
    private String categoryName;
    @SerializedName("CategoryDesc")
    @Expose
    private String categoryDesc;

    /**
     * No args constructor for use in serialization
     *
     */
    public TblCategory() {
    }

    /**
     *
     * @param categoryName
     * @param tblItem
     * @param categoryId
     * @param categoryDesc
     */
    public TblCategory(List<TblItem> tblItem, Integer categoryId, String categoryName, String categoryDesc) {
        super();
        this.tblItem = tblItem;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryDesc = categoryDesc;
    }

    public List<TblItem> getTblItem() {
        return tblItem;
    }

    public void setTblItem(List<TblItem> tblItem) {
        this.tblItem = tblItem;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

}