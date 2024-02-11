package com.omarInc.mymeal.model;

import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("idCategory")
    private String idCategory;

    @SerializedName("strCategory")
    private String strCategory;

    @SerializedName("strCategoryDescription")
    private String strCategoryDescription;
    @SerializedName("strCategoryThumb")
    private String strCategoryThumb;

    public String getStrCategoryThumb() {
        return strCategoryThumb;
    }

    public void setStrCategoryThumb(String strCategoryThumb) {
        this.strCategoryThumb = strCategoryThumb;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public String getStrCategory() {
        return strCategory;
    }

    public void setStrCategory(String strCategory) {
        this.strCategory = strCategory;
    }

    public String getStrCategoryDescription() {
        return strCategoryDescription;
    }

    public void setStrCategoryDescription(String strCategoryDescription) {
        this.strCategoryDescription = strCategoryDescription;
    }
    @Override
    public String toString() {
        return "Category{" +
                "idCategory='" + idCategory + '\'' +
                ", strCategory='" + strCategory + '\'' +
                ", strCategoryDescription='" + strCategoryDescription + '\'' +
                ", strCategoryThumb='" + strCategoryThumb + '\'' +
                '}';
    }
}
