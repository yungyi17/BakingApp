package com.example.android.bakingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.model.RecipeSteps;
import com.example.android.bakingapp.model.SaveRecipeData;

import java.util.List;

import butterknife.BindView;

public class MainActivityAdapter
        extends RecyclerView.Adapter<MainActivityAdapter.MainActivityAdapterViewHolder> {

    private List<String> mRecipeName;
    private RecipeSteps objRecipeStep;
    private Context mContext;

    private SelectRecipeName sRecipeName;

    public interface SelectRecipeName {
        void onSelectRecipeName(List<String> ingredients, List<Integer> stepId,
                                List<String> stepShortDesc, List<String> stepDesc,
                                List<String> stepVideoUrl, List<String> stepImageUrl);
    }

    public MainActivityAdapter(Context context, SelectRecipeName selectRecipeName) {
        mContext = context;
        sRecipeName = selectRecipeName;
    }

    @NonNull
    @Override
    public MainActivityAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        int layoutForListItem = R.layout.main_item_list;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(layoutForListItem, viewGroup, false);

        return new MainActivityAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainActivityAdapterViewHolder mainViewHolder, int position) {
        String recipeName = mRecipeName.get(position);
        mainViewHolder.mMainRecipeName.setText(recipeName);
    }

    @Override
    public int getItemCount() {
        if (mRecipeName == null) return 0;
        return mRecipeName.size();
    }

    public class MainActivityAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        final TextView mMainRecipeName;
        //@BindView(R.id.main_recipe_name) TextView mMainRecipeName;

        public MainActivityAdapterViewHolder(View itemView) {
            super(itemView);
            mMainRecipeName = itemView.findViewById(R.id.main_recipe_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            // For ingredients
            List<String> strIngredients = null;

            // For steps
            List<Integer> stId = null;
            List<String> stShortDesc = null;
            List<String> stDesc = null;
            List<String> stVideoUrl = null;
            List<String> stImgUrl = null;

            // Get the target object
            objRecipeStep = SaveRecipeData.getRecipeSteps().get(position);

            switch (position) {
                case 0:
                    // For ingredients
                    strIngredients = SaveRecipeData.getFirstIngredients();
                    // For the data of steps
                    stId = objRecipeStep.getFirstRecipeStepId();
                    stShortDesc = objRecipeStep.getFirstRecipeShortDescription();
                    stDesc = objRecipeStep.getFirstRecipeDescription();
                    stVideoUrl = objRecipeStep.getFirstRecipeVideoUrl();
                    stImgUrl = objRecipeStep.getFirstRecipeThumbnailUrl();
                    break;
                case 1:
                    // For ingredients
                    strIngredients = SaveRecipeData.getSecondIngredients();
                    // For the data of steps
                    stId = objRecipeStep.getSecondRecipeStepId();
                    stShortDesc = objRecipeStep.getSecondRecipeShortDescription();
                    stDesc = objRecipeStep.getSecondRecipeDescription();
                    stVideoUrl = objRecipeStep.getSecondRecipeVideoUrl();
                    stImgUrl = objRecipeStep.getSecondRecipeThumbnailUrl();
                    break;
                case 2:
                    // For ingredients
                    strIngredients = SaveRecipeData.getThirdIngredients();
                    // For the data of steps
                    stId = objRecipeStep.getThirdRecipeStepId();
                    stShortDesc = objRecipeStep.getThirdRecipeShortDescription();
                    stDesc = objRecipeStep.getThirdRecipeDescription();
                    stVideoUrl = objRecipeStep.getThirdRecipeVideoUrl();
                    stImgUrl = objRecipeStep.getThirdRecipeThumbnailUrl();
                    break;
                case 3:
                    // For ingredients
                    strIngredients = SaveRecipeData.getFourthIngredients();
                    // For the data of steps
                    stId = objRecipeStep.getFourthRecipeStepId();
                    stShortDesc = objRecipeStep.getFourthRecipeShortDescription();
                    stDesc = objRecipeStep.getFourthRecipeDescription();
                    stVideoUrl = objRecipeStep.getFourthRecipeVideoUrl();
                    stImgUrl = objRecipeStep.getFourthRecipeThumbnailUrl();
                    break;
                default:
                    break;
            }

            sRecipeName.onSelectRecipeName(strIngredients, stId, stShortDesc, stDesc,
                    stVideoUrl, stImgUrl);
        }
    }

    public void setRecipeNames(List<String> recipeNames) {
        mRecipeName = recipeNames;
        notifyDataSetChanged();
    }
}
