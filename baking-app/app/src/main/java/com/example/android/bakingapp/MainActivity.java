package com.example.android.bakingapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.android.bakingapp.model.SetRecipeDetailData;
import com.example.android.bakingapp.utils.NetworkUtils;
import com.example.android.bakingapp.utils.ParseJsonDataUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<String>>,
        MainActivityAdapter.SelectRecipeName {

    private RecyclerView mRecyclerView;
    private MainActivityAdapter mAdapter;

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RECIPE_LOADER_ID = 3377;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.main_recycler_view);
        mAdapter = new MainActivityAdapter(this, this);

        if (findViewById(R.id.grid_linear_layout) != null) {
            // For tablet layout
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this,
                    2, GridLayoutManager.VERTICAL, false);
            mRecyclerView.setLayoutManager(gridLayoutManager);
        } else {
            // For phone layout
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(layoutManager);
        }

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        android.support.v4.app.LoaderManager.getInstance(this)
                .initLoader(RECIPE_LOADER_ID, null, this);
    }

    @SuppressLint("StaticFieldLeak")
    @NonNull
    @Override
    public Loader<List<String>> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new AsyncTaskLoader<List<String>>(this) {

            List<String> mRecipeNames = null;

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                if (mRecipeNames != null) {
                    deliverResult(mRecipeNames);
                } else {
                    forceLoad();
                }
            }

            @Nullable
            @Override
            public List<String> loadInBackground() {
                URL getUrl = NetworkUtils.buildUrl();

                try {
                    String recipeJsonString = NetworkUtils.getResponseFromHttpUrl(getUrl);
                    List<String> recipeNames = ParseJsonDataUtils
                            .getRecipeNamesFromJson(recipeJsonString);
                    return recipeNames;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                } catch (IOException e) {
                    e.printStackTrace();return null;

                }
            }

            @Override
            public void deliverResult(@Nullable List<String> data) {
                super.deliverResult(data);
                mRecipeNames = data;
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<String>> loader, List<String> strings) {
        if (strings != null) {
            mAdapter.setRecipeNames(strings);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<String>> loader) {

    }

    @Override
    public void onSelectRecipeName(List<String> ingredients, List<Integer> stepId,
                                   List<String> stepShortDesc, List<String> stepDesc,
                                   List<String> stepVideoUrl, List<String> stepImageUrl) {
        // Toast.makeText(this, "Ingredients: " + ingredients.size() + ", " + "STEP ID SIZE: " + stepId.size(), Toast.LENGTH_LONG).show();

        SetRecipeDetailData.setIngredients(ingredients);
        SetRecipeDetailData.setStepIds(stepId);
        SetRecipeDetailData.setStepShortDescription(stepShortDesc);
        SetRecipeDetailData.setStepDescription(stepDesc);
        SetRecipeDetailData.setStepVideoUrl(stepVideoUrl);
        SetRecipeDetailData.setStepImageUrl(stepImageUrl);

        Intent recipeIntent = new Intent(this, RecipeDetailActivity.class);
        startActivity(recipeIntent);
    }
}
