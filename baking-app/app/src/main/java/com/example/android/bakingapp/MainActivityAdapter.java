package com.example.android.bakingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MainActivityAdapter
        extends RecyclerView.Adapter<MainActivityAdapter.MainActivityAdapterViewHodler> {

    private List<String> mRecipeName;
    private Context mContext;

    public MainActivityAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public MainActivityAdapterViewHodler onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        int layoutForListItem = R.layout.main_item_list;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(layoutForListItem, viewGroup, false);

        return new MainActivityAdapterViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainActivityAdapterViewHodler mainViewHolder, int position) {
        String recipeName = mRecipeName.get(position);
        mainViewHolder.mMainRecipeName.setText(recipeName);
    }

    @Override
    public int getItemCount() {
        if (mRecipeName == null) return 0;
        return mRecipeName.size();
    }

    public class MainActivityAdapterViewHodler extends RecyclerView.ViewHolder {
        final TextView mMainRecipeName;

        public MainActivityAdapterViewHodler(View itemView) {
            super(itemView);
            mMainRecipeName = itemView.findViewById(R.id.main_recipe_name);
        }
    }

    public void setRecipeNames(List<String> recipeNames) {
        mRecipeName = recipeNames;
        notifyDataSetChanged();
    }
}
