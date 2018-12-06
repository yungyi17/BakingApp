package com.example.android.bakingapp.utils;

import com.example.android.bakingapp.model.RecipeSteps;
import com.example.android.bakingapp.model.SaveRecipeData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ParseJsonDataUtils {

    private static final String TAG = ParseJsonDataUtils.class.getSimpleName();

    public static List<String> getRecipeNamesFromJson(String bakingJsonStr) throws JSONException {

        final String JSON_RECIPE_ID = "id";
        final String JSON_RECIPE_NAME = "name";
        final String JSON_RECIPE_INGREDIENTS = "ingredients";
        final String JSON_RECIPE_INGREDIENTS_QUANTITY = "quantity";
        final String JSON_RECIPE_INGREDIENTS_MEASURE = "measure";
        final String JSON_RECIPE_INGREDIENTS_INGREDIENT = "ingredient";
        final String JSON_RECIPE_STEPS = "steps";
        final String JSON_RECIPE_STEPS_ID = "id";
        final String JSON_RECIPE_STEPS_SHORT_DESCRIPTION = "shortDescription";
        final String JSON_RECIPE_STEPS_DESCRIPTION = "description";
        final String JSON_RECIPE_STEPS_VIDEO_URL = "videoURL";
        final String JSON_RECIPE_STEPS_THUMBNAIL_URL = "thumbnailURL";

        List<Integer> recipeIds = new ArrayList<>();
        List<String> recipeNames = new ArrayList<>();
        // List<String> ingredients = new ArrayList<>();
        List<RecipeSteps> recipeSteps = new ArrayList<>();

        // Hold ingredients data according to each recipe id.
        List<String> ingredientsForRecipeId1 = new ArrayList<>();
        List<String> ingredientsForRecipeId2 = new ArrayList<>();
        List<String> ingredientsForRecipeId3 = new ArrayList<>();
        List<String> ingredientsForRecipeId4 = new ArrayList<>();

        List<Integer> recStepsId = new ArrayList<>();
        List<String> recStepsShortDesc = new ArrayList<>();
        List<String> recStepsDesc = new ArrayList<>();
        List<String> recStepsVideoUrl = new ArrayList<>();
        List<String> recStepsThumbnailUrl = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(bakingJsonStr);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.optJSONObject(i);

            int recipeId = jsonObject.optInt(JSON_RECIPE_ID);
            // Hold ingredients data temporarily
            String strIngredients = null;

            recipeIds.add(jsonObject.optInt(JSON_RECIPE_ID));
            recipeNames.add(jsonObject.optString(JSON_RECIPE_NAME));

            JSONArray ingredientsArray = jsonObject.optJSONArray(JSON_RECIPE_INGREDIENTS);
            for (int j = 0; j < ingredientsArray.length(); j++) {
                JSONObject ingredientObject = ingredientsArray.optJSONObject(j);

                double inQuantity = ingredientObject.optDouble(JSON_RECIPE_INGREDIENTS_QUANTITY);
                String inMeasure = ingredientObject.optString(JSON_RECIPE_INGREDIENTS_MEASURE);
                String inIngredient = ingredientObject
                        .optString(JSON_RECIPE_INGREDIENTS_INGREDIENT);

                strIngredients = inIngredient + " " + inQuantity + " " + inMeasure;
                // ingredients.add(strIngredients);

                if (recipeId == 1) {
                    ingredientsForRecipeId1.add(strIngredients);
                }

                if (recipeId == 2) {
                    ingredientsForRecipeId2.add(strIngredients);
                }

                if (recipeId == 3) {
                    ingredientsForRecipeId3.add(strIngredients);
                }

                if (recipeId == 4) {
                    ingredientsForRecipeId4.add(strIngredients);
                }
            }

            // Hold Nutella Pie ingredients
            if (recipeId == 1 && strIngredients != null) {
                SaveRecipeData.setFirstIngredients(ingredientsForRecipeId1);
            }

            // Hold Brownies ingredients
            if (recipeId == 2 && strIngredients != null) {
                SaveRecipeData.setSecondIngredients(ingredientsForRecipeId2);
            }

            // Hold Yellow Cake ingredients
            if (recipeId == 3 && strIngredients != null) {
                SaveRecipeData.setThirdIngredients(ingredientsForRecipeId3);
            }

            // Hold Cheesecake ingredients
            if (recipeId == 4 && strIngredients != null) {
                SaveRecipeData.setFourthIngredients(ingredientsForRecipeId4);
            }

            // Refresh for new recipe
            if (recipeId == 2 || recipeId == 3 || recipeId == 4) {
                recStepsId = new ArrayList<>();
                recStepsShortDesc = new ArrayList<>();
                recStepsDesc = new ArrayList<>();
                recStepsVideoUrl = new ArrayList<>();
                recStepsThumbnailUrl = new ArrayList<>();
            }

            JSONArray recipeStepsArray = jsonObject.optJSONArray(JSON_RECIPE_STEPS);
            for (int k = 0; k < recipeStepsArray.length(); k++) {
                JSONObject recipeStepsObject = recipeStepsArray.optJSONObject(k);

                recStepsId.add(recipeStepsObject.optInt(JSON_RECIPE_STEPS_ID));
                recStepsShortDesc.add(recipeStepsObject
                        .optString(JSON_RECIPE_STEPS_SHORT_DESCRIPTION));
                recStepsDesc.add(recipeStepsObject.optString(JSON_RECIPE_STEPS_DESCRIPTION));
                recStepsVideoUrl.add(recipeStepsObject.optString(JSON_RECIPE_STEPS_VIDEO_URL));
                recStepsThumbnailUrl.add(recipeStepsObject
                        .optString(JSON_RECIPE_STEPS_THUMBNAIL_URL));
            }

            if (recipeId == 1) {
                RecipeSteps objFirstRecipeSteps = new RecipeSteps(recipeId, recStepsId,
                        recStepsShortDesc, recStepsDesc, recStepsVideoUrl, recStepsThumbnailUrl);
                recipeSteps.add(objFirstRecipeSteps);
            }

            if (recipeId == 2) {
                RecipeSteps objSecondRecipeSteps = new RecipeSteps(recipeId, recStepsId,
                        recStepsShortDesc, recStepsDesc, recStepsVideoUrl, recStepsThumbnailUrl);
                recipeSteps.add(objSecondRecipeSteps);
            }

            if (recipeId == 3) {
                RecipeSteps objThirdRecipeSteps = new RecipeSteps(recipeId, recStepsId,
                        recStepsShortDesc, recStepsDesc, recStepsVideoUrl, recStepsThumbnailUrl);
                recipeSteps.add(objThirdRecipeSteps);
            }

            if (recipeId == 4) {
                RecipeSteps objFourthRecipeSteps = new RecipeSteps(recipeId, recStepsId,
                        recStepsShortDesc, recStepsDesc, recStepsVideoUrl, recStepsThumbnailUrl);
                recipeSteps.add(objFourthRecipeSteps);
            }
        }

        SaveRecipeData.setRecipeIds(recipeIds);
        SaveRecipeData.setRecipeNames(recipeNames);

        // SaveRecipeData.setIngredients(ingredients);
        SaveRecipeData.setRecipeSteps(recipeSteps);

        return SaveRecipeData.getRecipeNames();
    }
}
