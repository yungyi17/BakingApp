package com.example.android.bakingapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

public class RecipeStepDetailActivity extends AppCompatActivity {

    public static final String RECIPE_DESCRIPTION = "recipe-description";
    public static final String RECIPE_VIDEO_URL = "recipe-video-url";
    public static final String RECIPE_STEP_POSITION = "recipe-step-position";
    public static final String RECIPE_SHORT_DESC = "recipe-short-desc";

    private static final String TAG = RecipeStepDetailActivity.class.getSimpleName();

    private String mDescription;
    private String mVideoUrl;
    private List<String> mShortDescList;

    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private RecipeDetailAdapter mAdapter;

    private TextView mDescriptionTextView;
    private TextView mVideoNotAvailable;
    private TextView mPreviousStep;
    private TextView mNextStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);

        mPlayerView = findViewById(R.id.recipe_step_video);
        mDescriptionTextView = findViewById(R.id.recipe_step_description);
        mVideoNotAvailable = findViewById(R.id.recipe_step_video_not_available);
        mPreviousStep = findViewById(R.id.previous_step);
        mNextStep = findViewById(R.id.next_step);

        Intent intent = getIntent();
        mDescription = intent.getStringExtra(RECIPE_DESCRIPTION);
        mVideoUrl = intent.getStringExtra(RECIPE_VIDEO_URL);
        mShortDescList = intent.getStringArrayListExtra(RECIPE_SHORT_DESC);
        int mPosition = intent.getIntExtra(RECIPE_STEP_POSITION, -1);

        // Need adapter object to get item count and it can set new position of
        // the first or last item
        mAdapter = new RecipeDetailAdapter(this, mShortDescList);

        displayRecipeStep(mDescription, mPosition);

        Uri builtUri = Uri.parse(mVideoUrl).buildUpon().build();
        initializeVideoPlayer(builtUri);
    }

    private void initializeVideoPlayer(Uri videoUri) {
        // If video is NOT available
        if (mVideoUrl.isEmpty()) {
            mPlayerView.setVisibility(View.GONE);
            mVideoNotAvailable.setVisibility(View.VISIBLE);
        // If video is available
        } else {
            mVideoNotAvailable.setVisibility(View.GONE);
            mPlayerView.setVisibility(View.VISIBLE);

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

    private void displayRecipeStep(String description, final int position) {
        mDescriptionTextView.setText(description);

        // Get item count from the adapter
        final int itemCnt = mAdapter.getItemCount();

        mPreviousStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int prevPos = position - 1;
                if (prevPos < 0) {
                    prevPos = itemCnt - 1;
                }
                mDescription = SetRecipeDetailData.getStepDescription().get(prevPos);
                mVideoUrl = SetRecipeDetailData.getStepVideoUrl().get(prevPos);

                Bundle bundle = new Bundle();
                bundle.putString(RECIPE_DESCRIPTION, mDescription);
                bundle.putString(RECIPE_VIDEO_URL, mVideoUrl);
                bundle.putInt(RECIPE_STEP_POSITION, prevPos);
                bundle.putStringArrayList(RECIPE_SHORT_DESC, (ArrayList<String>) mShortDescList);

                Intent previousIntent = new Intent(getApplicationContext(), RecipeStepDetailActivity.class);
                previousIntent.putExtras(bundle);
                startActivity(previousIntent);
            }
        });

        mNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nextPos = position + 1;
                if (nextPos > itemCnt - 1) {
                    nextPos = 0;
                }
                mDescription = SetRecipeDetailData.getStepDescription().get(nextPos);
                mVideoUrl = SetRecipeDetailData.getStepVideoUrl().get(nextPos);

                Bundle b = new Bundle();
                b.putString(RECIPE_DESCRIPTION, mDescription);
                b.putString(RECIPE_VIDEO_URL, mVideoUrl);
                b.putInt(RECIPE_STEP_POSITION, nextPos);
                b.putStringArrayList(RECIPE_SHORT_DESC, (ArrayList<String>) mShortDescList);

                Intent nextStepIntent = new Intent(getApplicationContext(), RecipeStepDetailActivity.class);
                nextStepIntent.putExtras(b);
                startActivity(nextStepIntent);
            }
        });
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
        if (!mVideoUrl.isEmpty()) {
            releasePlayer();
        }
    }
}
