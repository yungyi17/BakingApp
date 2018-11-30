package com.example.android.bakingapp.model;

public class Ingredients {

    private double quantity;
    private String measure;
    private String ingredient;

    public Ingredients(double quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public double getQuantity() {
        return this.quantity;
    }

    public String getMeasure() {
        return this.measure;
    }

    public String getIngredient() {
        return this.ingredient;
    }
}