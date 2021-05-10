package com.example.finalproject;

import android.content.ComponentName;
import android.view.View;
import android.widget.SeekBar;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.finalproject.activities.MainActivity;
import com.example.finalproject.activities.NameActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class CreatePaletteScratchTest {

    @Rule
    public ActivityScenarioRule paletteTestRule = new ActivityScenarioRule<>(MainActivity.class);
    @Rule
    public IntentsTestRule<MainActivity> intentsTestRule = new IntentsTestRule<>(MainActivity.class);

    // user flow 1: create palette from scratch
    // navigate to generate section
    // click on scratch button
    // user seekBars to change colors
    // click on views to select
    // to save, click on save button
    // give palette a name
    // click save

    // test 1.
    // user follows the user flow
    @Test
    public void createPalette() {
        onView(withId(R.id.page_2)).perform(click());
        onView(withId(R.id.button_scratch)).perform(click());
        onView(withId(R.id.seekBarR)).perform(setProgress(200));
        onView(withId(R.id.seekBarG)).perform(setProgress(0));
        onView(withId(R.id.seekBarB)).perform(setProgress(0));
        onView(withId(R.id.color3_scratch)).perform(click());
        onView(withId(R.id.seekBarG)).perform(setProgress(200));
        onView(withId(R.id.seekBarR)).perform(setProgress(0));
        onView(withId(R.id.seekBarB)).perform(setProgress(0));
        onView(withId(R.id.color5_scratch)).perform(click());
        onView(withId(R.id.seekBarB)).perform(setProgress(200));
        onView(withId(R.id.seekBarG)).perform(setProgress(0));
        onView(withId(R.id.seekBarR)).perform(setProgress(0));
        onView(withId(R.id.button_save_scratch)).perform(click());
        intended(hasComponent(new ComponentName(getApplicationContext(), NameActivity.class)));
        // onView(withId(R.id.editText_name)).perform(typeText("Test Palette"));
        // onView(withId(R.id.button_name_save)).perform(click());
    }

    // test 1.
    // create palette with no modifications
    @Test
    public void createPaletteNoMod() {
        onView(withId(R.id.page_2)).perform(click());
        onView(withId(R.id.button_scratch)).perform(click());
        onView(withId(R.id.button_save_scratch)).perform(click());
        intended(hasComponent(new ComponentName(getApplicationContext(), NameActivity.class)));
        // onView(withId(R.id.editText_name)).perform(typeText("Test Palette"));
        // onView(withId(R.id.button_name_save)).perform(click());
    }

    public static ViewAction setProgress(final int progress) {
        return new ViewAction() {
            @Override
            public void perform(UiController uiController, View view) {
                SeekBar seekBar = (SeekBar) view;
                seekBar.setProgress(progress);
            }
            @Override
            public String getDescription() {
                return "Set a progress on a SeekBar";
            }
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(SeekBar.class);
            }
        };
    }
}