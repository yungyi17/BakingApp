package com.example.android.bakingapp;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.action.ViewActions.click;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityRecyclerViewTest {

    private static final String RECIPE_DETAIL_TITLE = "Recipe Detail List";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickMainActivityRecipeName_OpenRecipeDetailPage() {
        onView(ViewMatchers.withId(R.id.main_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withText(RECIPE_DETAIL_TITLE)).check(matches(isDisplayed()));
    }
}
