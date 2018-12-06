package com.example.android.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingapp.model.SetRecipeDetailData;

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
    public void onRecipeStepSelected(int position) {
        // Toast.makeText(this, "POSITION: " + position, Toast.LENGTH_SHORT).show();
        String stepDesc = SetRecipeDetailData.getStepDescription().get(position);
        String stepVideoUrl = SetRecipeDetailData.getStepVideoUrl().get(position);
        String stepImgUrl = SetRecipeDetailData.getStepImageUrl().get(position);

        Bundle bundle = new Bundle();
        bundle.putString("RecipeDescription", stepDesc);
        bundle.putString("RecipeVideoUrl", stepVideoUrl);
        bundle.putString("RecipeImageUrl", stepImgUrl);
        bundle.putInt("Position", position);

        Intent recipeStepIntent = new Intent(this, RecipeStepDetailActivity.class);
        recipeStepIntent.putExtras(bundle);
        startActivity(recipeStepIntent);
    }
}