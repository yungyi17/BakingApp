package com.example.android.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class RecipeDetailActivity extends AppCompatActivity
        implements RecipeDetailAdapter.SelectRecipeStep {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
    }

    @Override
    public void onRecipeStepSelected(int position) {
        Toast.makeText(this, "POSITION: " + position, Toast.LENGTH_SHORT).show();
    }
}