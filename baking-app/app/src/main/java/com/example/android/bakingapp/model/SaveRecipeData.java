package com.example.android.bakingapp.model;

import java.util.List;

public class SaveRecipeData {

    private static List<Integer> mRecipeIds;
    private static List<String> mRecipeNames;
    private static List<String> mFirstIngredients;
    private static List<String> mSecondIngredients;
    private static List<String> mThirdIngredients;
    private static List<String> mFourthIngredients;
    private static List<RecipeSteps> mRecipeSteps;

    public static void setRecipeIds(List<Integer> recipeIds) {
        mRecipeIds = recipeIds;
    }

    public static void setRecipeNames(List<String> recipeNames) {
        mRecipeNames = recipeNames;
    }

    public static void setFirstIngredients(List<String> ingredients) {
        mFirstIngredients = ingredients;
    }

    public static void setSecondIngredients(List<String> ingredients) {
        mSecondIngredients = ingredients;
    }

    public static void setThirdIngredients(List<String> ingredients) {
        mThirdIngredients = ingredients;
    }

    public static void setFourthIngredients(List<String> ingredients) {
        mFourthIngredients = ingredients;
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

    public static List<String> getFirstIngredients() {
        return mFirstIngredients;
    }

    public static List<String> getSecondIngredients() {
        return mSecondIngredients;
    }

    public static List<String> getThirdIngredients() {
        return mThirdIngredients;
    }

    public static List<String> getFourthIngredients() {
        return mFourthIngredients;
    }

    public static List<RecipeSteps> getRecipeSteps() {
        return mRecipeSteps;
    }
}
