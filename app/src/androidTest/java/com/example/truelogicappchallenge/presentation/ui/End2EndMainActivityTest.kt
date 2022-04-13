package com.example.truelogicappchallenge.presentation.ui

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.idling.CountingIdlingResource
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.truelogicappchallenge.R
import com.example.truelogicappchallenge.di.DispatchersModule
import com.example.truelogicappchallenge.di.UseCaseModule
import com.example.truelogicappchallenge.domain.usecase.GetListCharactersUseCase
import com.example.truelogicappchallenge.domain.usecase.HandleFavoritesUseCase
import com.example.truelogicappchallenge.onClickViewChild
import com.example.truelogicappchallenge.presentation.viewmodel.ListCharactersViewModel
import com.example.truelogicappchallenge.withImageResourceView
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Inject
import javax.inject.Singleton


@OptIn(ExperimentalCoroutinesApi::class)
@HiltAndroidTest
@LargeTest
@RunWith(MockitoJUnitRunner::class)
class End2EndMainActivityTest {

    private lateinit var context: Context
    private lateinit var countingIdlingResource1: CountingIdlingResource
    private lateinit var countingIdlingResource2: CountingIdlingResource
    private lateinit var scenario: ActivityScenario<MainActivity>

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var getListCharactersUseCase: GetListCharactersUseCase

    @Inject
    lateinit var handleFavoriteUseCase: HandleFavoritesUseCase

    @Inject
    @DispatchersModule.MainDispatcher
    lateinit var dispatcher: CoroutineDispatcher

    @BindValue
    lateinit var viewModel: ListCharactersViewModel

    @Before
    fun setUp() {
        hiltRule.inject()
        context = InstrumentationRegistry.getInstrumentation().targetContext
        viewModel = ListCharactersViewModel(getListCharactersUseCase, handleFavoriteUseCase, dispatcher)
        countingIdlingResource1 = CountingIdlingResource("CharactersAPICall")
        countingIdlingResource2 = CountingIdlingResource("UpdateFavoriteStatus")
        viewModel.setIdlingResourceAPICall(countingIdlingResource1)
        viewModel.setIdlingResourceUpdateFavorite(countingIdlingResource2)
        IdlingRegistry.getInstance().register(countingIdlingResource1)
        IdlingRegistry.getInstance().register(countingIdlingResource2)
    }

    @Test
    fun getListCharacters_successfulResponse_listIsShownOnScreen() {
        return runTest {
            val job = launch {
                scenario = ActivityScenario.launch(MainActivity::class.java)
            }

            job.join()

            onView(withId(R.id.progress_bar)).check(matches(CoreMatchers.not(isDisplayed())))
            onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
            onView(withId(R.id.empty_view)).check(matches(CoreMatchers.not(isDisplayed())))
        }
    }

    @Test
    fun handleFavorite_addOrRemoveFavorite_checkHeartButtonChanged(){
        return runTest {
            val job = launch {
                scenario = ActivityScenario.launch(MainActivity::class.java)
            }

            job.join()

            onView(withId(R.id.recycler_view)).perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<ListCharactersAdapter.ListCharacterHolder>(0, onClickViewChild(R.id.favoriteIcon)))

            onView(
                CoreMatchers.allOf(
                    withId(R.id.favoriteIcon),
                    withImageResourceView(R.drawable.ic_favorite),
                    ViewMatchers.withContentDescription("Favorite_0")
                )
            ).check(matches(isDisplayed()))
            onView(
                CoreMatchers.allOf(
                    withId(R.id.favoriteIcon),
                    withImageResourceView(R.drawable.ic_no_favorite),
                    ViewMatchers.withContentDescription("No_favorite_1")
                )
            ).check(matches(isDisplayed()))
            onView(
                CoreMatchers.allOf(
                    withId(R.id.favoriteIcon),
                    withImageResourceView(R.drawable.ic_no_favorite),
                    ViewMatchers.withContentDescription("No_favorite_2")
                )
            ).check(matches(isDisplayed()))

            onView(withId(R.id.recycler_view)).perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<ListCharactersAdapter.ListCharacterHolder>(0, onClickViewChild(R.id.favoriteIcon)))

            onView(
                CoreMatchers.allOf(
                    withId(R.id.favoriteIcon),
                    withImageResourceView(R.drawable.ic_no_favorite),
                    ViewMatchers.withContentDescription("No_favorite_0")
                )
            ).check(matches(isDisplayed()))
        }
    }

    @After
    fun tearDown(){
        IdlingRegistry.getInstance().unregister(countingIdlingResource1)
        IdlingRegistry.getInstance().unregister(countingIdlingResource2)
        scenario.close()
    }


}