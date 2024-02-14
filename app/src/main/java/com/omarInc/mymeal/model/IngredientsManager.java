package com.omarInc.mymeal.model;

import java.util.List;

public class IngredientsManager {
    private static IngredientsManager instance;
    private List<Ingredient> ingredients;

    private IngredientsManager() {}

    public static synchronized IngredientsManager getInstance() {
        if (instance == null) {
            instance = new IngredientsManager();
        }
        return instance;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }
}
