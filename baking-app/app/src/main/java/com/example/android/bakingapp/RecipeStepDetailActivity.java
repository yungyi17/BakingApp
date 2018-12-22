package com.example.android.bakingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecipeStepDetailActivity extends AppCompatActivity {

    public static final String RECIPE_DESCRIPTION = "recipe-description";
    public static final String RECIPE_VIDEO_URL = "recipe-video-url";
    public static final String RECIPE_THUMBNAIL_URL = "recipe-thumbnail-url";
    public static final String RECIPE_STEP_POSITION = "recipe-step-position";
    public static final String RECIPE_SHORT_DESC = "recipe-short-desc";
    public static final String RECIPE_SHORT_DESC_ARRAY_LIST = "recipe-short-desc-array-list";

    private static final String TAG = RecipeStepDetailActivity.class.getSimpleName();
    private static final String VIDEO_PLAYER_POSITION = "video-player-position";

    private String mDescription;
    private String mVideoUrl;
    private String mStepImageUrl;
    private String mShortDesc;
    private List<String> mShortDescArrayList;
    private long restorePlayerPosition;

    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private RecipeDetailAdapter mAdapter;

    private TextView mDescriptionTextView;
    private TextView mShortDescriptionTextView;
    private ImageView mVideoNotAvailable;
    private TextView mPreviousStep;
    private TextView mNextStep;

    private boolean isPlayerWhenReady;
    private boolean isRestarted = false;

    private Bundle restorePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);

        mPlayerView = findViewById(R.id.recipe_step_video);
        mDescriptionTextView = findViewById(R.id.recipe_step_description);
        mShortDescriptionTextView = findViewById(R.id.recipe_short_description);
        mVideoNotAvailable = findViewById(R.id.recipe_step_video_not_available);
        mPreviousStep = findViewById(R.id.previous_step);
        mNextStep = findViewById(R.id.next_step);

        Intent intent = getIntent();
        mDescription = intent.getStringExtra(RECIPE_DESCRIPTION);
        mVideoUrl = intent.getStringExtra(RECIPE_VIDEO_URL);
        mStepImageUrl = intent.getStringExtra(RECIPE_THUMBNAIL_URL);
        mShortDesc = intent.getStringExtra(RECIPE_SHORT_DESC);
        mShortDescArrayList = intent.getStringArrayListExtra(RECIPE_SHORT_DESC_ARRAY_LIST);
        int mPosition = intent.getIntExtra(RECIPE_STEP_POSITION, -1);

        // Need adapter object to get item count and it can set new position of
        // the first or last item
        mAdapter = new RecipeDetailAdapter(this, mShortDescArrayList);

        displayRecipeStep(mDescription, mShortDesc, mPosition);

        Uri builtUri = Uri.parse(mVideoUrl).buildUpon().build();
        initializeVideoPlayer(builtUri);

        if (savedInstanceState != null) {
            restorePlayerPosition = savedInstanceState.getLong(VIDEO_PLAYER_POSITION);
            isPlayerWhenReady = savedInstanceState.getBoolean("playerState");
            mExoPlayer.seekTo(restorePlayerPosition);
            mExoPlayer.setPlayWhenReady(isPlayerWhenReady);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Log.d(TAG, "onStart in RecipeStepDetailActivity" + " PLAYER: " + mExoPlayer);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Log.d(TAG, "onResume in RecipeStepDetailActivity" + " PLAYER: " + mExoPlayer);
        if (restorePlayer != null && isRestarted) {
            Uri builtUri = Uri.parse(mVideoUrl).buildUpon().build();
            initializeVideoPlayer(builtUri);

            restorePlayerPosition = restorePlayer.getLong(VIDEO_PLAYER_POSITION);
            isPlayerWhenReady = restorePlayer.getBoolean("playerState");
            mExoPlayer.seekTo(restorePlayerPosition);
            mExoPlayer.setPlayWhenReady(isPlayerWhenReady);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Log.d(TAG, "onPause in RecipeStepDetailActivity" + " PLAYER: " + mExoPlayer);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Log.d(TAG, "onStop in RecipeStepDetailActivity" + " PLAYER: " + mExoPlayer);
        if (mExoPlayer != null) {
            restorePlayer = new Bundle();
            long storePlayerPosition = mExoPlayer.getCurrentPosition();
            restorePlayer.putLong(VIDEO_PLAYER_POSITION, storePlayerPosition);

            boolean playerWhenReady = mExoPlayer.getPlayWhenReady();
            restorePlayer.putBoolean("playerState", playerWhenReady);
            //mExoPlayer.stop();
            if (!mVideoUrl.isEmpty()) releasePlayer();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // Log.d(TAG, "onRestart in RecipeStepDetailActivity" + " PLAYER: " + mExoPlayer);
        isRestarted = true;
    }

    private void initializeVideoPlayer(Uri videoUri) {
        // If video is NOT available
        if (mVideoUrl.isEmpty()) {
            mPlayerView.setVisibility(View.GONE);
            mVideoNotAvailable.setVisibility(View.VISIBLE);

            String imageExtensions = ".png .jpg .jpeg .gif .bmp";

            if (!mStepImageUrl.isEmpty() && mStepImageUrl.contains(imageExtensions)) {
                Picasso.with(this)
                        .load(mStepImageUrl)
                        .into(mVideoNotAvailable);
            } else if (!mStepImageUrl.isEmpty() && !mStepImageUrl.contains(imageExtensions)) {
                Picasso.with(this)
                        .load(mStepImageUrl)
                        .error(R.drawable.error_message)
                        .into(mVideoNotAvailable);
            } else if (mStepImageUrl.isEmpty()) {
                mStepImageUrl = null;
                Picasso.with(this)
                        .load(mStepImageUrl)
                        .placeholder(R.drawable.place_holder)
                        .into(mVideoNotAvailable);
                mStepImageUrl = "";
            }

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

    private void displayRecipeStep(String description, String shortDesc, final int position) {
        mDescriptionTextView.setText(description);
        mShortDescriptionTextView.setText(shortDesc);

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
                mShortDesc = SetRecipeDetailData.getStepShortDescription().get(prevPos);
                mVideoUrl = SetRecipeDetailData.getStepVideoUrl().get(prevPos);
                mStepImageUrl = SetRecipeDetailData.getStepImageUrl().get(prevPos);

                Bundle bundle = new Bundle();
                bundle.putString(RECIPE_DESCRIPTION, mDescription);
                bundle.putString(RECIPE_SHORT_DESC, mShortDesc);
                bundle.putString(RECIPE_VIDEO_URL, mVideoUrl);
                bundle.putString(RECIPE_THUMBNAIL_URL, mStepImageUrl);
                bundle.putInt(RECIPE_STEP_POSITION, prevPos);
                bundle.putStringArrayList(RECIPE_SHORT_DESC_ARRAY_LIST, (ArrayList<String>) mShortDescArrayList);

                if (mExoPlayer != null) {
                    //mExoPlayer.stop();
                    releasePlayer();
                }

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
                mShortDesc = SetRecipeDetailData.getStepShortDescription().get(nextPos);
                mVideoUrl = SetRecipeDetailData.getStepVideoUrl().get(nextPos);
                mStepImageUrl = SetRecipeDetailData.getStepImageUrl().get(nextPos);

                Bundle b = new Bundle();
                b.putString(RECIPE_DESCRIPTION, mDescription);
                b.putString(RECIPE_SHORT_DESC, mShortDesc);
                b.putString(RECIPE_VIDEO_URL, mVideoUrl);
                b.putString(RECIPE_THUMBNAIL_URL, mStepImageUrl);
                b.putInt(RECIPE_STEP_POSITION, nextPos);
                b.putStringArrayList(RECIPE_SHORT_DESC_ARRAY_LIST, (ArrayList<String>) mShortDescArrayList);

                if (mExoPlayer != null) {
                    //mExoPlayer.stop();
                    releasePlayer();
                }

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
        // Log.d(TAG, "onDestroy in RecipeStepDetailActivity" + " PLAYER: " + mExoPlayer);
        if (!mVideoUrl.isEmpty()) {
            releasePlayer();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mExoPlayer != null) {
            long storePlayerPosition = mExoPlayer.getCurrentPosition();
            outState.putLong(VIDEO_PLAYER_POSITION, storePlayerPosition);

            boolean playerWhenReady = mExoPlayer.getPlayWhenReady();
            outState.putBoolean("playerState", playerWhenReady);
        }
    }
}
