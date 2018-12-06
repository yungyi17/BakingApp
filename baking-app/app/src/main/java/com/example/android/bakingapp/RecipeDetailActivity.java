package com.example.android.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingapp.model.SetRecipeDetailData;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity
        implements RecipeDetailAdapter.SelectRecipeStep {

    private TextView mDispIngredients;
    private List<String> displayIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        mDispIngredients = findViewById(R.id.display_ingredients);
        displayIngredients = SetRecipeDetailData.getIngredients();
        for (String ingredient : displayIngredients) {
            mDispIngredients.append(" - " + ingredient + "\n\n");
        }
    }

    @Override
    public void onRecipeStepSelected(int position, List<String> shortDescription) {
        // Toast.makeText(this, "POSITION: " + position, Toast.LENGTH_SHORT).show();
        String stepDesc = SetRecipeDetailData.getStepDescription().get(position);
        String stepVideoUrl = SetRecipeDetailData.getStepVideoUrl().get(position);
        // String stepImgUrl = SetRecipeDetailData.getStepImageUrl().get(position);

        Bundle bundle = new Bundle();
        bundle.putString(RecipeStepDetailActivity.RECIPE_DESCRIPTION, stepDesc);
        bundle.putString(RecipeStepDetailActivity.RECIPE_VIDEO_URL, stepVideoUrl);
        bundle.putInt(RecipeStepDetailActivity.RECIPE_STEP_POSITION, position);
        bundle.putStringArrayList(RecipeStepDetailActivity.RECIPE_SHORT_DESC,
                (ArrayList<String>) shortDescription);

        Intent recipeStepIntent = new Intent(this, RecipeStepDetailActivity.class);
        recipeStepIntent.putExtras(bundle);
        startActivity(recipeStepIntent);
    }
}