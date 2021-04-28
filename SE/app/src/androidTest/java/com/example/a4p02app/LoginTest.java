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
public class LoginTest {

    private String testPass;
    private String testEmail;

    @Rule
    public ActivityScenarioRule<LoginActivity> loginRule = new ActivityScenarioRule<>(LoginActivity.class);


    @Before
    public void initValidString() {
        testEmail = "bj16hh@brocku.ca";
        testPass = "asdf1234";
    }

    @Test
    public void testLogin() {
        ActivityScenarioRule<MainActivity> mainRule = new ActivityScenarioRule<>(MainActivity.class);

        // Type text and then press the button.
        onView(withId(R.id.txtEmail)).perform(typeText(testEmail), closeSoftKeyboard());
        onView(withId(R.id.txtPassword)).perform(typeText(testPass), closeSoftKeyboard());


        // Check that the text was changed.
        onView(withId(R.id.txtPassword)).check(matches(withText(testPass)));
        onView(withId(R.id.txtEmail)).check(matches(withText(testEmail)));

        onView(withId(R.id.btnLogIn)).perform(click());
    }
}