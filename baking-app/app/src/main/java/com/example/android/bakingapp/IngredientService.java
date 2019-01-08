package com.example.android.bakingapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

public class IngredientService extends IntentService {

    public static final String ACTION_UPDATE_INGREDIENT_WIDGETS = "action-update-ingredient-widgets";

    public IngredientService() {
        super("IngredientService");
    }

    public static void startActionUpdateIngredientWidgets(Context context) {
        Intent intent = new Intent(context, IngredientService.class);
        intent.setAction(ACTION_UPDATE_INGREDIENT_WIDGETS);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_INGREDIENT_WIDGETS.equals(action)) {
                handleActionUpdateIngredientWidgets();
            }
        }
    }

    private void handleActionUpdateIngredientWidgets() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeIngredientWidget.class));
        //Trigger data update to handle the GridView widgets and force a data refresh
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);
        RecipeIngredientWidget.updateIngredientWidgets(this, appWidgetManager, appWidgetIds);
    }
}
