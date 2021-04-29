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
public class MoreInfoInstrumentedTest {

    private String fname;
    private String lname;
    private String phone;
    private String unit;
    private String street;
    private String postal;
    private String city;

    private String nponame;
    private String description;
    private String url;

    @Rule
    public ActivityScenarioRule<GetMoreInfo> signupRule = new ActivityScenarioRule<>(GetMoreInfo.class);


    @Before
    public void initValidString() {
        Random rand;
        rand = new Random();
        int r = rand.nextInt((9999 - 100) + 1) + 10;

        fname = "Unit";
        lname = "Test";
        phone = "1112223333";
        unit = "1";
        street = "Google Ave";
        postal = "L0L 0L0";
        city = "SanFran";

        nponame = "A foundation";
        description = "We do things";
        url = "google.net";
    }

    @Test
    public void collectInfo(){

        //Advanced Info
        onView(withId(R.id.firstname)).perform(typeText(fname), closeSoftKeyboard());
        onView(withId(R.id.lastname)).perform(typeText(lname), closeSoftKeyboard());
        onView(withId(R.id.phone)).perform(typeText(phone), closeSoftKeyboard());
        onView(withId(R.id.unit)).perform(typeText(unit), closeSoftKeyboard());
        onView(withId(R.id.street)).perform(typeText(street), closeSoftKeyboard());
        onView(withId(R.id.postal)).perform(typeText(postal), closeSoftKeyboard());
        onView(withId(R.id.city)).perform(typeText(city), closeSoftKeyboard());

        onView(withId(R.id.nponame)).perform(typeText(nponame), closeSoftKeyboard());
        onView(withId(R.id.description)).perform(typeText(description), closeSoftKeyboard());
        onView(withId(R.id.url)).perform(typeText(url), closeSoftKeyboard());

        onView(withId(R.id.save)).perform(click());
    }
}