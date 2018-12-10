package com.example.android.bakingapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingapp.model.SetRecipeDetailData;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity
        implements RecipeDetailAdapter.SelectRecipeStep {

    private TextView mDispIngredients;
    private TextView mVideoNotAvailable;
    private TextView mDispInitText;
    private List<String> displayIngredients;

    // Primitive type can be saved onSaveInstanceState initially even though
    // no step was selected, which may be leading to an error.
    private Integer mPosition = null;

    private String mVideoUrl;

    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;

    private boolean mTwoPane;
    private int mPreviousPos = 1000;
    private int initCounter = 0;

    private static final String SAVE_RECIPE_POSITION = "save-recipe-position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        if (findViewById(R.id.recipe_detail_linear_layout) != null) {
            // For init text
            initCounter++;
            // For tablet layout
            mTwoPane = true;

            // Avoid displaying blank, display initial text when a tablet runs initially
            if (initCounter == 1) {
                // No highlight for a step
                RecipeDetailAdapter.selectedPosition = 1000;
                mDispInitText = findViewById(R.id.init_text_view);
                mDispInitText.setVisibility(View.VISIBLE);
                mDispInitText.setTextSize(27);
                mDispInitText.setText(getString(R.string.recipe_step_detail_initializer));
            }
        } else {
            // For phone layout
            mTwoPane = false;
        }

        mDispIngredients = findViewById(R.id.display_ingredients);
        displayIngredients = SetRecipeDetailData.getIngredients();
        for (String ingredient : displayIngredients) {
            mDispIngredients.append(" - " + ingredient + "\n\n");
        }

        if (savedInstanceState != null && mTwoPane
                && (savedInstanceState.containsKey(SAVE_RECIPE_POSITION))) {
            mPosition = savedInstanceState.getInt(SAVE_RECIPE_POSITION);

            // Keep track the highlighted position.
            RecipeDetailAdapter.selectedPosition = mPosition;

            displayTabletLayout(mPosition);
        }
    }

    @Override
    public void onRecipeStepSelected(int position, List<String> shortDescription) {
        mPosition = position;

        if (mTwoPane) {
            displayTabletLayout(position);
            mPreviousPos = position;
        } else {
            String stepDesc = SetRecipeDetailData.getStepDescription().get(position);
            String stepVideoUrl = SetRecipeDetailData.getStepVideoUrl().get(position);

            Bundle bundle = new Bundle();
            bundle.putString(RecipeStepDetailActivity.RECIPE_DESCRIPTION, stepDesc);
            bundle.putString(RecipeStepDetailActivity.RECIPE_VIDEO_URL, stepVideoUrl);
            bundle.putInt(RecipeStepDetailActivity.RECIPE_STEP_POSITION, position);
            bundle.putStringArrayList(RecipeStepDetailActivity.RECIPE_SHORT_DESC,
                    (ArrayList<String>) shortDescription);

            Intent recipeStepIntent = new Intent(this, RecipeStepDetailActivity.class);
            recipeStepIntent.putExtras(bundle);
            startActivity(recipeStepIntent);
        }
    }

    private void displayTabletLayout(int position) {
        mDispInitText.setVisibility(View.GONE);

        TextView mDescriptionTextView;
        String mDescription;

        mDescription = SetRecipeDetailData.getStepDescription().get(position);
        mVideoUrl = SetRecipeDetailData.getStepVideoUrl().get(position);

        mPlayerView = findViewById(R.id.recipe_step_video);
        mDescriptionTextView = findViewById(R.id.recipe_step_description);
        mVideoNotAvailable = findViewById(R.id.recipe_step_video_not_available);

        mDescriptionTextView.setText(mDescription);

        Uri builtUri = Uri.parse(mVideoUrl).buildUpon().build();
        initializeVideoPlayer(builtUri, position);
    }

    private void initializeVideoPlayer(Uri videoUri, int pos) {
        // If video is NOT available
        if (mVideoUrl.isEmpty()) {
            mPlayerView.setVisibility(View.GONE);
            mVideoNotAvailable.setVisibility(View.VISIBLE);
        } else {
            // Video is available
            mVideoNotAvailable.setVisibility(View.GONE);
            mPlayerView.setVisibility(View.VISIBLE);

            // As a user clicks another step on a tablet, the video that corresponds
            // to the step is not playing properly. The video for the step that a user
            // clicked first keeps playing even though another step is clicked because
            // mExoPlayer is NOT null.
            if (pos != mPreviousPos) mExoPlayer = null;

            if (mExoPlayer == null) {
                TrackSelector trackSelector = new DefaultTrackSelector();
                LoadControl loadControl = new DefaultLoadControl();

                mExoPlayer = ExoPlayerFactory
                        .newSimpleInstance(this, trackSelector, loadControl);
                mPlayerView.setPlayer(mExoPlayer);

                String userAgent = Util.getUserAgent(this, "BakingApp");
                MediaSource mediaSource = new ExtractorMediaSource(videoUri,
                        new DefaultDataSourceFactory(this, userAgent),
                        new DefaultExtractorsFactory(), null, null);

                mExoPlayer.prepare(mediaSource);
                mExoPlayer.setPlayWhenReady(true);
            }
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mPosition != null) {
            outState.putInt(SAVE_RECIPE_POSITION, mPosition);
        }
    }
}