package com.example.android.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

import static android.content.Context.MODE_PRIVATE;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeIngredientWidget extends AppWidgetProvider {

    private static final String TAG = "RecipeIngredientWidget";
    private static final String PREFS_NAME_FOR_RECIPE_ID = "prefs-name-for-recipe-id";
    private static final String PREFS_UPDATED_PREFIX_KEY = "prefs_updated_prefix_key_";
    public static final String ACTION_WIDGET_CONFIGURE_CLICKED = "action-widget-configure-clicked";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = getRecipeListRemoteView(context, appWidgetId);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        IngredientService.startActionUpdateIngredientWidgets(context);
    }

    public static void updateIngredientWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
        Log.d(TAG, "onEnabled will be displayed");
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        Log.d(TAG, "onDisabled will be displayed");
    }

    /**
     * Creates and returns the RemoteViews to be displayed in the GridView mode widget
     *
     * @param context The context
     * @return The RemoteViews for the GridView mode widget
     */
    private static RemoteViews getRecipeListRemoteView(Context context, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_list_view);

        boolean updateIngredients;

        SharedPreferences prefs = context
                .getSharedPreferences(PREFS_NAME_FOR_RECIPE_ID, MODE_PRIVATE);
        updateIngredients = prefs.getBoolean(PREFS_UPDATED_PREFIX_KEY + appWidgetId, false);

        // Set the GridWidgetService intent to act as the adapter for the GridView
        Intent intent = new Intent(context, GridWidgetService.class);
        intent.putExtra("appWidgetIdForIngredients", appWidgetId);
        views.setRemoteAdapter(R.id.widget_list_view, intent);

        Intent configIntent = new Intent(context, RecipeIngredientsWidgetConfigure.class);
        configIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        configIntent.putExtra(ACTION_WIDGET_CONFIGURE_CLICKED, true);
        PendingIntent configPendingIntent = PendingIntent.getActivity(context,
                appWidgetId, configIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.change_ingredients_button, configPendingIntent);

        // Log.d(TAG, "Ingredients Updated: " + ingredientsUpdated);

        if (updateIngredients) {
            // Start Ingredient Update Service handler
            Intent ingredientIntent = new Intent(context, IngredientService.class);
            ingredientIntent.setAction(IngredientService.ACTION_UPDATE_INGREDIENT_WIDGETS);
            context.startService(ingredientIntent);

            SharedPreferences prefsBoolean = context
                    .getSharedPreferences(PREFS_NAME_FOR_RECIPE_ID, MODE_PRIVATE);
            SharedPreferences.Editor editor = prefsBoolean.edit();
            editor.putBoolean(PREFS_UPDATED_PREFIX_KEY + appWidgetId, false);
            editor.apply();
        }

        return views;
    }
}

