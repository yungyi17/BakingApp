package com.example.android.bakingapp.model;

import java.util.List;

public class SaveRecipeData {

    private static List<Integer> mRecipeIds;
    private static List<String> mRecipeNames;
    private static List<Ingredients> mIngredients;
    private static List<RecipeSteps> mRecipeSteps;

    public static void setRecipeIds(List<Integer> recipeIds) {
        mRecipeIds = recipeIds;
    }

    public static void setRecipeNames(List<String> recipeNames) {
        mRecipeNames = recipeNames;
    }

    public static void setIngredients(List<Ingredients> ingredients) {
        mIngredients = ingredients;
    }

    public static void setRecipeSteps(List<RecipeSteps> recipeSteps) {
        mRecipeSteps = recipeSteps;
    }

    public static List<Integer> getRecipeIds() {
        return mRecipeIds;
    }

    public static List<String> getRecipeNames() {
        return mRecipeNames;
    }

    public static List<Ingredients> getIngredients() {
        return mIngredients;
    }

    public static List<RecipeSteps> getRecipeSteps() {
        return mRecipeSteps;
    }
}
