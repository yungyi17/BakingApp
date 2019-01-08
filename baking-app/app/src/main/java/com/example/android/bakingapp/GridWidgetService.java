package com.example.android.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
        return new GridRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}

class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = "GridWidgetService";
    private static final String PREFS_NAME_FOR_RECIPE_ID = "prefs-name-for-recipe-id";
    private static final String PREF_PREFIX_KEY = "prefix_";

    private Context mContext;
    private List<String> mWidgetRecipeIngredients;
    private int mAppWidgetId;

    public GridRemoteViewsFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;
        this.mAppWidgetId = intent.getIntExtra("appWidgetIdForIngredients", -1);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public int getCount() {
        if (mWidgetRecipeIngredients == null) return 0;
        return mWidgetRecipeIngredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (mWidgetRecipeIngredients == null || mWidgetRecipeIngredients.size() == 0) return null;

        String recipeIngredient = mWidgetRecipeIngredients.get(position);

        RemoteViews views = new RemoteViews(mContext
                .getPackageName(), R.layout.recipe_ingredient_widget);

        views.setTextViewText(R.id.widget_recipe_name, recipeIngredient);

        return views;
    }

    // Called on start and when notifyAppWidgetViewDataChanged is called
    @Override
    public void onDataSetChanged() {
        URL getUrl = NetworkUtils.buildUrl();

        try {
            String recipeJsonString = NetworkUtils.getResponseFromHttpUrl(getUrl);
            ParseJsonDataUtils.getRecipeNamesFromJson(recipeJsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SharedPreferences prefs = mContext
                .getSharedPreferences(PREFS_NAME_FOR_RECIPE_ID, Context.MODE_PRIVATE);
        int recipeId = prefs.getInt(PREF_PREFIX_KEY + mAppWidgetId, -1);

        switch (recipeId) {
            case 1:
                mWidgetRecipeIngredients = SaveRecipeData.getFirstIngredients();
                break;
            case 2:
                mWidgetRecipeIngredients = SaveRecipeData.getSecondIngredients();
                break;
            case 3:
                mWidgetRecipeIngredients = SaveRecipeData.getThirdIngredients();
                break;
            case 4:
                mWidgetRecipeIngredients = SaveRecipeData.getFourthIngredients();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        // mWidgetRecipeIngredients.clear();
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
