package com.dicoding.courseschedule

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.dicoding.courseschedule.ui.add.AddCourseActivity
import com.dicoding.courseschedule.ui.home.HomeActivity
import org.junit.Before
import org.junit.Test

class HomeActivityTest {
    @Before
    fun setup() {
        ActivityScenario.launch(HomeActivity::class.java)
        Intents.init()
    }

    @Test
    fun assertActionMenuAddCourse() {
        onView(withId(R.id.action_add)).check(matches(isDisplayed()))
        onView(withId(R.id.action_add)).perform(click())

        intended(hasComponent(AddCourseActivity::class.java.name))
    }
}
