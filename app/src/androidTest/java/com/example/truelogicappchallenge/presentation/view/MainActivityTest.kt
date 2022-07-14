package com.example.truelogicappchallenge.presentation.view

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.truelogicappchallenge.*
import com.example.truelogicappchallenge.di.UseCaseModule
import com.example.truelogicappchallenge.domain.helper.ResultDomain
import com.example.truelogicappchallenge.domain.usecase.GetListCharactersUseCase
import com.example.truelogicappchallenge.domain.usecase.HandleFavoritesUseCase
import com.example.truelogicappchallenge.presentation.model.CharacterView
import com.example.truelogicappchallenge.presentation.view.appcompat.activity.MainActivity
import com.example.truelogicappchallenge.presentation.view.appcompat.adapter.ListCharactersAdapter
import com.example.truelogicappchallenge.presentation.viewmodel.ListCharactersViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
@UninstallModules(UseCaseModule::class)
@HiltAndroidTest
@LargeTest
@RunWith(MockitoJUnitRunner::class)
class MainActivityTest {

    private lateinit var context: Context
    private lateinit var scenario: ActivityScenario<MainActivity>

    val captorName: ArgumentCaptor<String> = ArgumentCaptor.forClass(String::class.java)
    val captorIsFavorite: ArgumentCaptor<Boolean> = ArgumentCaptor.forClass(Boolean::class.java)

    @Suppress("UNCHECKED_CAST")
    private fun <T> capture(captor: ArgumentCaptor<T>): T = captor.capture()

    @BindValue @Mock
    lateinit var getListCharactersUseCase: GetListCharactersUseCase

    @BindValue @Mock
    lateinit var handleFavoriteUseCase: HandleFavoritesUseCase

    @BindValue
    lateinit var viewModel: ListCharactersViewModel

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @Rule(order = 1)
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp(){
        context = InstrumentationRegistry.getInstrumentation().targetContext
        viewModel = ListCharactersViewModel(getListCharactersUseCase, handleFavoriteUseCase, UnconfinedTestDispatcher())
    }

    @Test
    fun getListCharacters_successfulResponse_listIsShownOnScreen(){

        return runTest {

            val job = launch {
                succesfulResponse()
                scenario = ActivityScenario.launch(MainActivity::class.java)
            }

            job.join()

            onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())))
            onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
            onView(withId(R.id.empty_view)).check(matches(not(isDisplayed())))
        }
    }

    @Test
    fun handleFavorite_addOrRemoveFavorite_checkHeartButtonChanged(){
        return runTest {
            val job = launch {
                setFavoriteCase()
                scenario = ActivityScenario.launch(MainActivity::class.java)
            }

            job.join()

            onView(withId(R.id.recycler_view)).perform(
                RecyclerViewActions
                .actionOnItemAtPosition<ListCharactersAdapter.ListCharacterHolder>(0, onClickViewChild(R.id.favoriteIcon)))

            onView(allOf(withId(R.id.favoriteIcon), withImageResourceView(R.drawable.ic_favorite), withContentDescription("Favorite_0"))).check(matches(isDisplayed()))
            onView(allOf(withId(R.id.favoriteIcon), withImageResourceView(R.drawable.ic_no_favorite), withContentDescription("No_favorite_1"))).check(matches(isDisplayed()))
            onView(allOf(withId(R.id.favoriteIcon), withImageResourceView(R.drawable.ic_no_favorite), withContentDescription("No_favorite_2"))).check(matches(isDisplayed()))

            verify(handleFavoriteUseCase, times(1)).updateFavoriteStatus(capture(captorName), capture(captorIsFavorite))
            verify(getListCharactersUseCase, times(2)).getRepositoryData()
            assertThat(captorName.value).isEqualTo("name 0")
            assertThat(captorIsFavorite.value).isEqualTo(false)
        }
    }

    @Test
    fun getListCharacters_failureResponse_errorMessageIsShown(){

        return runTest {

            val job = launch {
                failureResponse()
                scenario = ActivityScenario.launch(MainActivity::class.java)
            }

            job.join()

            onView(withId(R.id.progress_bar)).check(matches(not(isDisplayed())))
            onView(withId(R.id.recycler_view)).check(matches(not(isDisplayed())))
            onView(withId(R.id.empty_view)).check(matches(isDisplayed()))
            onView(withId(R.id.empty_view)).check(matches(withText("There is an error message")))
        }

    }

    @After
    fun tearDown() {
        scenario.close()
    }

    private suspend fun succesfulResponse(){

        val list = ArrayList<CharacterView>()

        list.add(CharacterView("name 0","nickname 0", "https://images.amcnetworks.com/amc.com/wp-content/uploads/2015/04/cast_bb_700x1000_walter-white-lg.jpg", false))
        list.add(CharacterView("name 1","nickname 1", "https://vignette.wikia.nocookie.net/breakingbad/images/9/95/JesseS5.jpg/revision/latest?cb=20120620012441", false))
        list.add(CharacterView("name 2","nickname 2", "https://s-i.huffpost.com/gen/1317262/images/o-ANNA-GUNN-facebook.jpg", false))

        Mockito.`when`(getListCharactersUseCase.getRepositoryData()).thenReturn(
            ResultDomain.Success(list)
        )
    }

    private suspend fun failureResponse() {
        Mockito.`when`(getListCharactersUseCase.getRepositoryData()).thenReturn(
            ResultDomain.Failure("There is an error message")
        )
    }

    private suspend fun setFavoriteCase() {
        Mockito.`when`(handleFavoriteUseCase.updateFavoriteStatus(any(), any())).thenReturn(Unit)

        val listWithoutFavorites = ArrayList<CharacterView>()

        listWithoutFavorites.add(CharacterView("name 0","nickname 0", "https://images.amcnetworks.com/amc.com/wp-content/uploads/2015/04/cast_bb_700x1000_walter-white-lg.jpg", false))
        listWithoutFavorites.add(CharacterView("name 1","nickname 1", "https://vignette.wikia.nocookie.net/breakingbad/images/9/95/JesseS5.jpg/revision/latest?cb=20120620012441", false))
        listWithoutFavorites.add(CharacterView("name 2","nickname 2", "https://s-i.huffpost.com/gen/1317262/images/o-ANNA-GUNN-facebook.jpg", false))

        val listWithFavorite0 = ArrayList<CharacterView>()

        listWithFavorite0.add(CharacterView("name 0","nickname 0", "https://images.amcnetworks.com/amc.com/wp-content/uploads/2015/04/cast_bb_700x1000_walter-white-lg.jpg", true))
        listWithFavorite0.add(CharacterView("name 1","nickname 1", "https://vignette.wikia.nocookie.net/breakingbad/images/9/95/JesseS5.jpg/revision/latest?cb=20120620012441", false))
        listWithFavorite0.add(CharacterView("name 2","nickname 2", "https://s-i.huffpost.com/gen/1317262/images/o-ANNA-GUNN-facebook.jpg", false))

        Mockito.`when`(getListCharactersUseCase.getRepositoryData())
            .thenReturn(ResultDomain.Success(listWithoutFavorites))
            .thenReturn(ResultDomain.Success(listWithFavorite0))
    }

}