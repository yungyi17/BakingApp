package com.example.android.bakingapp;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecipeDetailAdapter
        extends RecyclerView.Adapter<RecipeDetailAdapter.RecipeDetailViewHolder> {

    static int selectedPosition = 0;

    private List<String> mShortDescription;

    private Context mContext;
    private SelectRecipeStep mCallback;

    public interface SelectRecipeStep {
        void onRecipeStepSelected(int itemPos, List<String> shortDesc);
    }

    public RecipeDetailAdapter(Context context,
                               List<String> shortDesc) {
        mContext = context;
        mShortDescription = shortDesc;
    }

    @NonNull
    @Override
    public RecipeDetailViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.recipe_detail_item_list,
                viewGroup, false);

        return new RecipeDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeDetailViewHolder recipeViewHolder,
                                 int position) {
        String stepShortDescription = mShortDescription.get(position);

        recipeViewHolder.itemView
                .setBackgroundColor(selectedPosition
                        == position ? Color.GRAY : Color.TRANSPARENT);

        recipeViewHolder.mStepShortDesc.setText(stepShortDescription);
    }

    @Override
    public int getItemCount() {
        if (mShortDescription == null) return 0;
        return mShortDescription.size();
    }

    public class RecipeDetailViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        final TextView mStepShortDesc;

        public RecipeDetailViewHolder(View view) {
            super(view);
            mStepShortDesc = view.findViewById(R.id.step_short_description);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position == RecyclerView.NO_POSITION) return;

            notifyItemChanged(selectedPosition);
            selectedPosition = getAdapterPosition();
            notifyItemChanged(selectedPosition);

            mCallback = (SelectRecipeStep) mContext;
            mCallback.onRecipeStepSelected(position, mShortDescription);
        }
    }
}
