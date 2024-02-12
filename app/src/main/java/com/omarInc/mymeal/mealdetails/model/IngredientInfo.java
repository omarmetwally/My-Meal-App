package com.omarInc.mymeal.mealdetails.model;

public class IngredientInfo {
    private String ingredient;
    private String measure;
    private String imageUrl;

    public IngredientInfo(String ingredient, String measure, String imageUrl) {
        this.ingredient = ingredient;
        this.measure = measure;
        this.imageUrl = imageUrl;
    }

    public String getIngredient() { return ingredient; }
    public String getMeasure() { return measure; }
    public String getImageUrl() { return imageUrl; }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
