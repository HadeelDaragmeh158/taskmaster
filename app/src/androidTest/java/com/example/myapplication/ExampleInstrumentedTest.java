package com.example.myapplication;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Rule
    public ActivityScenarioRule<MainActivity> rule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
        public void useAppContext() {
            // Context of the app under test.
            Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
            assertEquals("com.example.myapplication", appContext.getPackageName());
        }
//    @Test
//    public void validateUITest() {
//        onView(withId(R.id.title).matches(matches((withText("My Tasks"))));
//        onView(withId(R.id.txt_username)).check(matches(withText("NewT")));
//        onView(withId(R.id.imageView)).check(matches((isDisplayed())));
//
//    }
//
@Test
public void navigateToSettingsScreenTest() {

    onView(withId(R.id.sittingButton)).perform(click());

    onView(withId(R.id.editTextTextPersonName)).perform(typeText("NewTitle"),
            closeSoftKeyboard());
    onView(withId(R.id.saveButton)).perform(click());

    onView(withId(R.id.editTextTextPersonName)).check(matches((withText("NewTitle"))));

}

    @Test
    public void validateUITest() {
//        onView(withId(R.id.titleMyTask).check(matches((withText("My Task")))));
        onView(withId(R.id.username)).check(matches(withText("New ")));
        onView(withId(R.id.imagebutton)).check(matches((isDisplayed())));

    }


//    @Test
//    public void navigateToSettingsScreenTest() {
//
//        onView(withId(R.id.sittingButton)).perform(click());
//
//        onView(withId(R.id.editTextTextPersonName)).perform(typeText("New "),
//                closeSoftKeyboard());
//        onView(withId(R.id.saveButton)).perform(click());
//
//        onView(withId(R.id.erName)).check(matches((withText("New "))));
//
//    }
    @Test
    public void AddTaskScreenTest() {

        onView(withId(R.id.addTaskButton)).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.myTask)).perform(typeText("Hadeel"),
                closeSoftKeyboard());
        onView(withId(R.id.doSomthing)).perform(typeText("t10"),
                closeSoftKeyboard());
        onView(withId(R.id.addTask_addTaskPage)).perform(click());


    }
}