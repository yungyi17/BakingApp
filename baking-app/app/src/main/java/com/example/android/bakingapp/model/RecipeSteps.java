package com.example.android.bakingapp.model;

public class RecipeSteps {

    private int recipeStepId;
    private String recipeShortDescription;
    private String recipeDescription;
    private String recipeVideoUrl;
    private String recipeThumbnailUrl;

    public RecipeSteps(int rId, String rShortDesc,
                       String rDesc, String videoUrl, String rThumbnailUrl) {
        recipeStepId = rId;
        recipeShortDescription = rShortDesc;
        recipeDescription = rDesc;
        recipeVideoUrl = videoUrl;
        recipeThumbnailUrl = rThumbnailUrl;
    }

    public int getRecipeStepId() {
        return recipeStepId;
    }

    public String getRecipeShortDescription() {
        return recipeShortDescription;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public String getRecipeVideoUrl() {
        return recipeVideoUrl;
    }

    public String getRecipeThumbnamilUrl() {
        return recipeThumbnailUrl;
    }
}
