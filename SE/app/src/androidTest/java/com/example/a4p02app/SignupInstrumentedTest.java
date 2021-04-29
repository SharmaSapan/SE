package com.example.a4p02app;

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
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SignupInstrumentedTest {

    private Random rand;
    private String testPass;
    private String testEmail;
    private String testConfirm;

    @Rule
    public ActivityScenarioRule<SignUpActivity> signupRule = new ActivityScenarioRule<>(SignUpActivity.class);


    @Before
    public void initValidString() {
        rand = new Random();
        int r = rand.nextInt((9999 - 100) + 1) + 10;
        testEmail = "user_" + Integer.toString(r) + "@test.junit";
        testPass = "asdf1234";
        testConfirm = testPass;
    }

    @Test
    public void testSignUp() {
        onView(withId(R.id.txtEmail)).perform(typeText(testEmail), closeSoftKeyboard());
        onView(withId(R.id.txtPassword)).perform(typeText(testPass), closeSoftKeyboard());
        onView(withId(R.id.txtConfirm)).perform(typeText(testConfirm), closeSoftKeyboard());
        onView(withId(R.id.rdoBusiness)).perform(click());

        onView(withId(R.id.txtPassword)).check(matches(withText(testPass)));
        onView(withId(R.id.txtEmail)).check(matches(withText(testEmail)));
        onView(withId(R.id.txtConfirm)).check(matches(withText(testPass)));
        onView(withId(R.id.rdoPersonal)).check(matches(isNotChecked()));

        onView(withId(R.id.btnSignUp)).perform(click());
    }
}