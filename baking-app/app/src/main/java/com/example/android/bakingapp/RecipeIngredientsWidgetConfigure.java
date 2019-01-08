package com.example.android.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

public class RecipeIngredientsWidgetConfigure extends AppCompatActivity {

    private static final String TAG = RecipeIngredientsWidgetConfigure.class.getSimpleName();

    private static final String PREFS_NAME_FOR_RECIPE_ID = "prefs-name-for-recipe-id";
    private static final String PREF_PREFIX_KEY = "prefix_";
    private static final String PREFS_UPDATED_PREFIX_KEY = "prefs_updated_prefix_key_";

    private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private int mRecipeSelectedId = 0;

    public RecipeIngredientsWidgetConfigure() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if they press the back button.
        setResult(RESULT_CANCELED);

        setContentView(R.layout.activity_recipe_ingredients_widget_configure);

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            // Log.d(TAG, "Widget ID in Configure Class: " + mAppWidgetId);
        }
        // If they gave us an intent without the widget id, just bail.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        final Context context = RecipeIngredientsWidgetConfigure.this;

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_nutella_pie:
                if (checked) mRecipeSelectedId = 1;
                    break;
            case R.id.radio_brownies:
                if (checked) mRecipeSelectedId = 2;
                    break;
            case R.id.radio_yellow_cake:
                if (checked) mRecipeSelectedId = 3;
                    break;
            case R.id.radio_cheesecake:
                if (checked) mRecipeSelectedId = 4;
                    break;
            default:
                break;
        }

        saveRecipeIdSelected(context, mAppWidgetId, mRecipeSelectedId);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        RecipeIngredientWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);

        // Make sure we pass back the original appWidgetId
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }

    private void saveRecipeIdSelected(Context context, int appWidgetId, int recipeId) {
        boolean wasClicked = getIntent().getBooleanExtra(RecipeIngredientWidget
                .ACTION_WIDGET_CONFIGURE_CLICKED, false);

        SharedPreferences prefs = context
                .getSharedPreferences(PREFS_NAME_FOR_RECIPE_ID, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(PREF_PREFIX_KEY + appWidgetId, recipeId);
        editor.putBoolean(PREFS_UPDATED_PREFIX_KEY + appWidgetId, wasClicked);
        editor.apply();
    }
}
