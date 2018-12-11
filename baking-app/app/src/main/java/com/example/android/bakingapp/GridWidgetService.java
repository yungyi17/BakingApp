package com.example.android.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.model.SaveRecipeData;
import com.example.android.bakingapp.utils.NetworkUtils;
import com.example.android.bakingapp.utils.ParseJsonDataUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class GridWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext());
    }
}

class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private List<String> mWidgetRecipeName;
    private List<Integer> mWidgetRecipeId;

    public GridRemoteViewsFactory(Context applicationContext) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public int getCount() {
        if (mWidgetRecipeName == null) return 0;
        return mWidgetRecipeName.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (mWidgetRecipeId == null || mWidgetRecipeId.size() == 0) return null;

        String recipeName = mWidgetRecipeName.get(position);
        int recipeId = mWidgetRecipeId.get(position);

        RemoteViews views = new RemoteViews(mContext
                .getPackageName(), R.layout.recipe_ingredient_widget);

        views.setTextViewText(R.id.widget_recipe_name, recipeName);

        // Fill in the onClick PendingIntent Template using the specific recipe Id
        // for each item individually
        Bundle extras = new Bundle();
        extras.putInt(IngredientActivity.RECIPE_EXTRA_ID, recipeId);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        views.setOnClickFillInIntent(R.id.widget_recipe_name, fillInIntent);

        return views;
    }

    // Called on start and when notifyAppWidgetViewDataChanged is called
    @Override
    public void onDataSetChanged() {
        URL getUrl = NetworkUtils.buildUrl();

        try {
            String recipeJsonString = NetworkUtils.getResponseFromHttpUrl(getUrl);
            mWidgetRecipeName = ParseJsonDataUtils.getRecipeNamesFromJson(recipeJsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mWidgetRecipeId = SaveRecipeData.getRecipeIds();
    }

    @Override
    public void onDestroy() {
        mWidgetRecipeId.clear();
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }
}
