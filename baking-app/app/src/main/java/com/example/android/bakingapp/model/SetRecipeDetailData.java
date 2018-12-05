package com.example.android.bakingapp.model;

import java.util.ArrayList;
import java.util.List;

public class SetRecipeDetailData {

    private static List<String> mIngredients = new ArrayList<>();
    private static List<Integer> mStepIds = new ArrayList<>();
    private static List<String> mStepShortDesc = new ArrayList<>();
    private static List<String> mStepDesc = new ArrayList<>();
    private static List<String> mStepVideoUrl = new ArrayList<>();
    private static List<String> mStepImgUrl = new ArrayList<>();

    public static void setIngredients(List<String> ingredients) {
        mIngredients = ingredients;
    }

    public static void setStepIds(List<Integer> stepIds) {
        mStepIds = stepIds;
    }

    public static void setStepShortDescription(List<String> shortDescription) {
        mStepShortDesc = shortDescription;
    }

    public static void setStepDescription(List<String> description) {
        mStepDesc = description;
    }

    public static void setStepVideoUrl(List<String> stepVideoUrl) {
        mStepVideoUrl = stepVideoUrl;
    }

    public static void setStepImageUrl(List<String> stepImageUrl) {
        mStepImgUrl = stepImageUrl;
    }

    public static List<String> getIngredients() {
        return mIngredients;
    }

    public static List<Integer> getStepIds() {
        return mStepIds;
    }

    public static List<String> getStepShortDescription() {
        return mStepShortDesc;
    }

    public static List<String> getStepDescription() {
        return mStepDesc;
    }

    public static List<String> getStepVideoUrl() {
        return mStepVideoUrl;
    }

    public static List<String> getStepImageUrl() {
        return mStepImgUrl;
    }
}
