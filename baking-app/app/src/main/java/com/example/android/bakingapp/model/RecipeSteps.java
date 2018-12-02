package com.example.android.bakingapp.model;

import java.util.List;

public class RecipeSteps {

    private List<Integer> firstRecipeStepId;
    private List<String> firstRecipeShortDescription;
    private List<String> firstRecipeDescription;
    private List<String> firstRecipeVideoUrl;
    private List<String> firstRecipeThumbnailUrl;

    private List<Integer> secondRecipeStepId;
    private List<String> secondRecipeShortDescription;
    private List<String> secondRecipeDescription;
    private List<String> secondRecipeVideoUrl;
    private List<String> secondRecipeThumbnailUrl;

    private List<Integer> thirdRecipeStepId;
    private List<String> thirdRecipeShortDescription;
    private List<String> thirdRecipeDescription;
    private List<String> thirdRecipeVideoUrl;
    private List<String> thirdRecipeThumbnailUrl;

    private List<Integer> fourthRecipeStepId;
    private List<String> fourthRecipeShortDescription;
    private List<String> fourthRecipeDescription;
    private List<String> fourthRecipeVideoUrl;
    private List<String> fourthRecipeThumbnailUrl;

    public RecipeSteps(int recipeId, List<Integer> rId, List<String> rShortDesc,
                       List<String> rDesc, List<String> videoUrl, List<String> rThumbnailUrl) {
        if (recipeId == 1) {
            firstRecipeStepId = rId;
            firstRecipeShortDescription = rShortDesc;
            firstRecipeDescription = rDesc;
            firstRecipeVideoUrl = videoUrl;
            firstRecipeThumbnailUrl = rThumbnailUrl;
        }

        if (recipeId == 2) {
            secondRecipeStepId = rId;
            secondRecipeShortDescription = rShortDesc;
            secondRecipeDescription = rDesc;
            secondRecipeVideoUrl = videoUrl;
            secondRecipeThumbnailUrl = rThumbnailUrl;
        }

        if (recipeId == 3) {
            thirdRecipeStepId = rId;
            thirdRecipeShortDescription = rShortDesc;
            thirdRecipeDescription = rDesc;
            thirdRecipeVideoUrl = videoUrl;
            thirdRecipeThumbnailUrl = rThumbnailUrl;
        }

        if (recipeId == 4) {
            fourthRecipeStepId = rId;
            fourthRecipeShortDescription = rShortDesc;
            fourthRecipeDescription = rDesc;
            fourthRecipeVideoUrl = videoUrl;
            fourthRecipeThumbnailUrl = rThumbnailUrl;
        }
    }

    public List<Integer> getFirstRecipeStepId() {
        return firstRecipeStepId;
    }

    public List<String> getFirstRecipeShortDescription() {
        return firstRecipeShortDescription;
    }

    public List<String> getFirstRecipeDescription() {
        return firstRecipeDescription;
    }

    public List<String> getFirstRecipeVideoUrl() {
        return firstRecipeVideoUrl;
    }

    public List<String> getFirstRecipeThumbnailUrl() {
        return firstRecipeThumbnailUrl;
    }

    public List<Integer> getSecondRecipeStepId() {
        return secondRecipeStepId;
    }

    public List<String> getSecondRecipeShortDescription() {
        return secondRecipeShortDescription;
    }

    public List<String> getSecondRecipeDescription() {
        return secondRecipeDescription;
    }

    public List<String> getSecondRecipeVideoUrl() {
        return secondRecipeVideoUrl;
    }

    public List<String> getSecondRecipeThumbnailUrl() {
        return secondRecipeThumbnailUrl;
    }

    public List<Integer> getThirdRecipeStepId() {
        return thirdRecipeStepId;
    }

    public List<String> getThirdRecipeShortDescription() {
        return thirdRecipeShortDescription;
    }

    public List<String> getThirdRecipeDescription() {
        return thirdRecipeDescription;
    }

    public List<String> getThirdRecipeVideoUrl() {
        return thirdRecipeVideoUrl;
    }

    public List<String> getThirdRecipeThumbnailUrl() {
        return thirdRecipeThumbnailUrl;
    }

    public List<Integer> getFourthRecipeStepId() {
        return fourthRecipeStepId;
    }

    public List<String> getFourthRecipeShortDescription() {
        return fourthRecipeShortDescription;
    }

    public List<String> getFourthRecipeDescription() {
        return fourthRecipeDescription;
    }

    public List<String> getFourthRecipeVideoUrl() {
        return fourthRecipeVideoUrl;
    }

    public List<String> getFourthRecipeThumbnailUrl() {
        return fourthRecipeThumbnailUrl;
    }
}
