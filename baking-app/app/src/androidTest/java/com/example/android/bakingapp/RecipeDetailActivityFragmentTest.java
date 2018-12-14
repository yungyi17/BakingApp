package com.example.android.bakingapp;

import static android.support.test.espresso.Espresso.onView;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RecipeDetailActivityFragmentTest {

    private static final String RECIPE_STEP_TITLE = "Recipe steps";

    private static final Intent MY_ACTIVITY_INTENT =
            new Intent(InstrumentationRegistry.getTargetContext(), RecipeDetailActivity.class);

    @Rule
    public ActivityTestRule<RecipeDetailActivity> mActivityTestRule
            = new ActivityTestRule<>(RecipeDetailActivity.class,
            false, false);

    @Before
    public void setup() {
        mActivityTestRule.launchActivity(MY_ACTIVITY_INTENT);
    }

    @Test
    public void checkRecipeStepTitleDisplay() {
        onView(withId(R.id.recipe_step_label)).check(matches(withText(RECIPE_STEP_TITLE)));
    }

    @Test
    public void checkDynamicallyCreatedFragment() {
        RecipeDetailActivityFragment fragment = new RecipeDetailActivityFragment();
        mActivityTestRule.getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_recipe_detail, fragment).commit();
    }
}
