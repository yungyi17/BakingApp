package com.example.android.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.example.android.bakingapp.model.SaveRecipeData;

import java.util.List;

public class IngredientActivity extends AppCompatActivity {

    public static final String RECIPE_EXTRA_ID = "recipe-extra-id";
    private static final int DEFAULT_EXTRA_ID = -1;
    private int mRecipeId;

    private String mIngredientTitle;

    private List<String> mRecipeIngredients;
    private TextView mIngredientsView;
    private TextView mIngredientTitleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);

        mIngredientsView = findViewById(R.id.recipe_ingredients_view);
        mIngredientTitleView = findViewById(R.id.recipe_ingredient_title);
        mRecipeId = getIntent().getIntExtra(RECIPE_EXTRA_ID, DEFAULT_EXTRA_ID);

        displayRecipeIngredients();
    }

    private void displayRecipeIngredients() {
        switch (mRecipeId) {
            case 1:
                mRecipeIngredients = SaveRecipeData.getFirstIngredients();
                mIngredientTitle = SaveRecipeData.getRecipeNames().get(0);
                break;
            case 2:
                mRecipeIngredients = SaveRecipeData.getSecondIngredients();
                mIngredientTitle = SaveRecipeData.getRecipeNames().get(1);
                break;
            case 3:
                mRecipeIngredients = SaveRecipeData.getThirdIngredients();
                mIngredientTitle = SaveRecipeData.getRecipeNames().get(2);
                break;
            case 4:
                mRecipeIngredients = SaveRecipeData.getFourthIngredients();
                mIngredientTitle = SaveRecipeData.getRecipeNames().get(3);
                break;
            default:
                break;
        }

        String titleSource = mIngredientTitle + " " + "Ingredient";
        mIngredientTitleView.setText(titleSource);

        for (String ingredient : mRecipeIngredients) {
            mIngredientsView.append("- " + ingredient + "\n\n");
        }
    }
}
