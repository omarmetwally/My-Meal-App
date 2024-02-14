package com.omarInc.mymeal.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Ingredient  {
    @SerializedName("idIngredient")
    private String idIngredient;
    @SerializedName("strIngredient")
    private String strIngredient;
    @SerializedName("strDescription")
    private String strDescription;
    @SerializedName("IngredientImageUrl")

    private String IngredientImageUrl;

    public String getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(String idIngredient) {
        this.idIngredient = idIngredient;
    }

    public String getStrIngredient() {
        return strIngredient;
    }

    public void setStrIngredient(String strIngredient) {
        this.strIngredient = strIngredient;
    }

    public String getStrDescription() {
        return strDescription;
    }

    public void setStrDescription(String strDescription) {
        this.strDescription = strDescription;
    }

    public String getIngredientImageUrl() {
      return "https://www.themealdb.com/images/ingredients/" + strIngredient + ".png";
    }

    public void setIngredientImageUrl(String ingredientImageUrl) {
        IngredientImageUrl = ingredientImageUrl;
    }


}
