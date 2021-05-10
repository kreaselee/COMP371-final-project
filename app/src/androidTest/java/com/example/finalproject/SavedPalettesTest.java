package com.example.finalproject;

import android.content.ComponentName;
import android.content.Context;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.finalproject.activities.MainActivity;
import com.example.finalproject.activities.NameActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.service.autofill.Validators.not;
import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.junit.Assert.*;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SavedPalettesTest {

    @Rule
    public ActivityScenarioRule paletteTestRule = new ActivityScenarioRule<>(MainActivity.class);
    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule = new IntentsTestRule<>(MainActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.finalproject", appContext.getPackageName());
    }

    // user flow 1: generate random palette
    // navigate to generate section
    // click on random button
    // to save, click on save button
    // give palette a name
    // click save

    // test 1.
    // user follows the user flow
    @Test
    public void generatePalette() {
        onView(withId(R.id.page_2)).perform(click());
        onView(withId(R.id.button_random)).perform(click());
        onView(withId(R.id.button_generate_save)).perform(click());
        intended(hasComponent(new ComponentName(getApplicationContext(), NameActivity.class)));
        onView(withId(R.id.editText_name)).perform(typeText("Test Palette"));
        // onView(withId(R.id.button_name_save)).perform(click());
    }

    // test 2.
    // save palette with empty string
    @Test
    public void generatePaletteEmpty() {
        onView(withId(R.id.page_2)).perform(click());
        onView(withId(R.id.button_random)).perform(click());
        onView(withId(R.id.button_generate_save)).perform(click());
        intended(hasComponent(new ComponentName(getApplicationContext(), NameActivity.class)));
        onView(withId(R.id.editText_name)).perform(typeText(""));
        onView(withId(R.id.button_name_save)).perform(click());
    }
}