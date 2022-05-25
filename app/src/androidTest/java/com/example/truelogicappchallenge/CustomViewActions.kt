package com.example.truelogicappchallenge

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

fun onClickViewChild(viewId: Int) = object : ViewAction {
    override fun getConstraints(): Matcher<View> {
        return ViewMatchers.isDisplayed()
    }

    override fun getDescription(): String {
        return "Click on a child view with specified id."
    }

    override fun perform(uiController: UiController?, view: View?) {
        val v = view?.findViewById<View>(viewId)
        v?.performClick()
    }

}

fun withImageResourceView(resourceId: Int) = object : TypeSafeMatcher<View>(){
    override fun describeTo(description: Description?) {
        description?.appendText("Item has ID resource")
    }

    override fun matchesSafely(view: View?): Boolean {
        val tag = view?.tag

        return tag == resourceId
    }

}