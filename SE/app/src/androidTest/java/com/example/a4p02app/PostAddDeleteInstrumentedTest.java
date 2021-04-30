package com.example.a4p02app;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class PostAddDeleteInstrumentedTest {

    private String title;
    private String item;
    private String desc;
    private String dropLoc;
    private String tags;

    @Rule
    public ActivityScenarioRule<MainActivity> mainRule = new ActivityScenarioRule<>(MainActivity.class);


    @Before
    public void initValidString() {
        title = "JUnit Test";
        item = "Instrumented Test";
        desc = "This is only a test. Don't panic!";
        dropLoc = "10 Google Lane";
        tags = "Test, Junit";
    }

    @Test
    public void testPost() {

        //Load Editplist
        onView(withId(R.id.post)).perform(click());
        onView(withId(R.id.postFrag)).check(matches(isDisplayed()));

        //Fill Boxes
        onView(withId(R.id.Title)).perform(typeText(title), closeSoftKeyboard());
        onView(withId(R.id.Item)).perform(typeText(item), closeSoftKeyboard());
        onView(withId(R.id.Description)).perform(typeText(desc), closeSoftKeyboard());
        onView(withId(R.id.DropoffLocation)).perform(typeText(dropLoc), closeSoftKeyboard());
        onView(withId(R.id.Tags)).perform(typeText(tags), closeSoftKeyboard());

        onView(withId(R.id.Post)).perform(click());
        onView(withId(R.id.homeFrag)).check(matches(isDisplayed()));

        //Always select newest @ pos 0
        onView(withId(R.id.plist)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.postDetails)).check(matches(isDisplayed()));

        onView(withId(R.id.deletePost)).perform(click());
    }
}