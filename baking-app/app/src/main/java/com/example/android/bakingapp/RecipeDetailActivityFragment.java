package com.example.android.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.model.SetRecipeDetailData;

public class RecipeDetailActivityFragment extends Fragment {

    private static final String TAG = RecipeDetailActivityFragment.class.getSimpleName();

    public RecipeDetailActivityFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Log.d(TAG, "onCreateView in Fragment");
        final View rootView = inflater.inflate(R.layout.fragment_recipe_detail_activity,
                container, false);

        RecyclerView mRecyclerView = rootView.findViewById(R.id.fragment_detail_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        RecipeDetailAdapter detailAdapter = new RecipeDetailAdapter(getContext(),
                SetRecipeDetailData.getStepShortDescription());

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(detailAdapter);

        return rootView;
    }
}
