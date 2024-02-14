package com.omarInc.mymeal.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class IngredientsResponse implements Serializable {
    @SerializedName("meals")
    private List<Ingredient> ingredients;

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
