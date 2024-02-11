package com.omarInc.mymeal.ingredients.view;

import com.omarInc.mymeal.model.Ingredient;

import java.util.List;

public interface IngredientsView {
    void showIngredients(List<Ingredient> ingredients);
    void showError(String message);
}
