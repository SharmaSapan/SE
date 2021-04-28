package com.example.a4p02app;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityInstrumentedTest {


    @Rule
    public ActivityScenarioRule<MainActivity> mainRule = new ActivityScenarioRule<>(MainActivity.class);


    @Test
    public void testNavBar() {
        //Load Home
        onView(withId(R.id.home)).perform(click());
        onView(withId(R.id.homeFrag)).check(matches(isDisplayed()));

        //Load Profile
        onView(withId(R.id.profile)).perform(click());
        onView(withId(R.id.profileFrag)).check(matches(isDisplayed()));

        //Load NPO
        onView(withId(R.id.npolist)).perform(click());
        onView(withId(R.id.npoFrag)).check(matches(isDisplayed()));

        //Load Fav
        onView(withId(R.id.favs)).perform(click());
        onView(withId(R.id.favFrag)).check(matches(isDisplayed()));

        //Load Edit
        onView(withId(R.id.post)).perform(click());
        onView(withId(R.id.postFrag)).check(matches(isDisplayed()));

    }
}