package com.example.android.bakingapp.utils;

import com.example.android.bakingapp.model.Ingredients;
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
        List<Ingredients> ingredients = new ArrayList<>();
        List<RecipeSteps> recipeSteps = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(bakingJsonStr);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.optJSONObject(i);

            recipeIds.add(jsonObject.optInt(JSON_RECIPE_ID));
            recipeNames.add(jsonObject.optString(JSON_RECIPE_NAME));

            JSONArray ingredientsArray = jsonObject.optJSONArray(JSON_RECIPE_INGREDIENTS);
            for (int j = 0; j < ingredientsArray.length(); j++) {
                JSONObject ingredientObject = ingredientsArray.optJSONObject(j);

                double inQuantity = ingredientObject.optDouble(JSON_RECIPE_INGREDIENTS_QUANTITY);
                String inMeasure = ingredientObject.optString(JSON_RECIPE_INGREDIENTS_MEASURE);
                String inIngredient = ingredientObject
                        .optString(JSON_RECIPE_INGREDIENTS_INGREDIENT);

                Ingredients objIngredients
                        = new Ingredients(inQuantity, inMeasure, inIngredient);
                ingredients.add(objIngredients);
            }

            JSONArray recipeStepsArray = jsonObject.optJSONArray(JSON_RECIPE_STEPS);
            for (int k = 0; k < recipeStepsArray.length(); k++) {
                JSONObject recipeStepsObject = recipeStepsArray.optJSONObject(k);

                int recStepsId = recipeStepsObject.optInt(JSON_RECIPE_STEPS_ID);
                String recStepsShortDesc = recipeStepsObject
                        .optString(JSON_RECIPE_STEPS_SHORT_DESCRIPTION);
                String recStepsDesc = recipeStepsObject.optString(JSON_RECIPE_STEPS_DESCRIPTION);
                String recStepsVideoUrl = recipeStepsObject.optString(JSON_RECIPE_STEPS_VIDEO_URL);
                String recStepsThumbnailUrl = recipeStepsObject
                        .optString(JSON_RECIPE_STEPS_THUMBNAIL_URL);

                RecipeSteps objRecipeSteps = new RecipeSteps(recStepsId, recStepsShortDesc,
                        recStepsDesc, recStepsVideoUrl, recStepsThumbnailUrl);
                recipeSteps.add(objRecipeSteps);
            }
        }

        SaveRecipeData.setRecipeIds(recipeIds);
        SaveRecipeData.setRecipeNames(recipeNames);

        SaveRecipeData.setIngredients(ingredients);
        SaveRecipeData.setRecipeSteps(recipeSteps);

        return SaveRecipeData.getRecipeNames();
    }
}
