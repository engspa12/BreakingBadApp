package com.example.truelogicappchallenge.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.filters.SmallTest
import com.example.truelogicappchallenge.domain.helper.DataState
import com.example.truelogicappchallenge.domain.usecase.GetListCharactersUseCase
import com.example.truelogicappchallenge.domain.usecase.HandleFavoritesUseCase
import com.example.truelogicappchallenge.presentation.model.CharacterView
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule
import org.mockito.kotlin.*

@OptIn(ExperimentalCoroutinesApi::class)
@SmallTest
@RunWith(MockitoJUnitRunner::class)
class ListCharactersViewModelTest {

    @Mock
    lateinit var getListCharactersUseCase: GetListCharactersUseCase

    @Mock
    lateinit var handleFavoritesUseCase: HandleFavoritesUseCase

    @Mock
    lateinit var listObserver: Observer<List<CharacterView>>

    @Mock
    lateinit var errorMessageObserver: Observer<String?>

    @Mock
    lateinit var emptyViewObserver: Observer<Boolean?>

    @Mock
    lateinit var progressBarObserver: Observer<Boolean?>

    @Mock
    lateinit var containerObserver: Observer<Boolean?>

    lateinit var SUT: ListCharactersViewModel

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Captor
    lateinit var captorList: ArgumentCaptor<List<CharacterView>>

    @Captor
    lateinit var captorErrorString: ArgumentCaptor<String>

    @Captor
    lateinit var captorEmptyView: ArgumentCaptor<Boolean>

    @Captor
    lateinit var captorProgressBar: ArgumentCaptor<Boolean>

    @Captor
    lateinit var captorContainer: ArgumentCaptor<Boolean>

    val captorName: ArgumentCaptor<String> = ArgumentCaptor.forClass(String::class.java)
    val captorIsFavorite: ArgumentCaptor<Boolean> = ArgumentCaptor.forClass(Boolean::class.java)

    @Suppress("UNCHECKED_CAST")
    private fun <T> capture(captor: ArgumentCaptor<T>): T = captor.capture()

    @Before
    fun setUp(){
        SUT = ListCharactersViewModel(getListCharactersUseCase, handleFavoritesUseCase, UnconfinedTestDispatcher())
        SUT.listCharacters.observeForever(listObserver)
        SUT.errorMessage.observeForever(errorMessageObserver)
        SUT.emptyView.observeForever(emptyViewObserver)
        SUT.progressBar.observeForever(progressBarObserver)
        SUT.container.observeForever(containerObserver)
    }

    @Test
    fun getListCharacters_successfulResponse_listIsSentToUI(){
        return runTest {
            val job = launch {
                successfulResponse()
            }

            job.join()

            SUT.getListCharacters()

            verify(getListCharactersUseCase, times(1)).getRepositoryData()
            verify(handleFavoritesUseCase, never()).updateFavoriteStatus(any(), any())

            verify(listObserver, times(1)).onChanged(captorList.capture())
            verify(errorMessageObserver, never()).onChanged(any())
            verify(emptyViewObserver, times(2)).onChanged(captorEmptyView.capture())
            verify(progressBarObserver, times(2)).onChanged(captorProgressBar.capture())
            verify(containerObserver, times(2)).onChanged(captorContainer.capture())

            assertThat(captorList.value[0].name).isEqualTo("name 1")
            assertThat(captorList.value[1].name).isEqualTo("name 3")
            assertThat(captorList.value[2].name).isEqualTo("name 2")
            assertThat(captorList.value[3].name).isEqualTo("name 4")

            assertThat(captorEmptyView.firstValue).isEqualTo(false)
            assertThat(captorEmptyView.secondValue).isEqualTo(false)

            assertThat(captorProgressBar.firstValue).isEqualTo(true)
            assertThat(captorProgressBar.secondValue).isEqualTo(false)

            assertThat(captorContainer.firstValue).isEqualTo(false)
            assertThat(captorContainer.secondValue).isEqualTo(true)
        }
    }

    @Test
    fun getListCharacters_failureResponse_errorIsSentToUI() {
        return runTest {
            val job = launch {
                failureResponse()
            }

            job.join()

            SUT.getListCharacters()

            verify(getListCharactersUseCase, times(1)).getRepositoryData()
            verify(handleFavoritesUseCase, never()).updateFavoriteStatus(any(), any())

            verify(listObserver, never()).onChanged(any())
            verify(errorMessageObserver, times(1)).onChanged(captorErrorString.capture())
            verify(emptyViewObserver, times(2)).onChanged(captorEmptyView.capture())
            verify(progressBarObserver, times(2)).onChanged(captorProgressBar.capture())
            verify(containerObserver, times(2)).onChanged(captorContainer.capture())

            assertThat(captorErrorString.value).isEqualTo("Error when retrieving data from usecase")

            assertThat(captorEmptyView.firstValue).isEqualTo(false)
            assertThat(captorEmptyView.secondValue).isEqualTo(true)

            assertThat(captorProgressBar.firstValue).isEqualTo(true)
            assertThat(captorProgressBar.secondValue).isEqualTo(false)

            assertThat(captorContainer.firstValue).isEqualTo(false)
            assertThat(captorContainer.secondValue).isEqualTo(false)
        }
    }

    @Test
    fun updateFavoriteStatus_favoriteDataIsSentToUsecase() {
        return runTest {

            val job = launch {
                successfulResponse()
            }

            job.join()

            SUT.getListCharacters()
            SUT.updateFavoriteStatus(2)

            verify(getListCharactersUseCase, times(2)).getRepositoryData()
            verify(handleFavoritesUseCase, times(1)).updateFavoriteStatus(capture(captorName), capture(captorIsFavorite))

            assertThat(captorName.value).isEqualTo("name 2")
            assertThat(captorIsFavorite.value).isEqualTo(false)
        }
    }

    private suspend fun successfulResponse() {

        val listCharacterView = ArrayList<CharacterView>()

        listCharacterView.add(CharacterView("name 1", "nickname 1", "img 1", true))
        listCharacterView.add(CharacterView("name 3", "nickname 3", "img 3", true))
        listCharacterView.add(CharacterView("name 2", "nickname 2", "img 2", false))
        listCharacterView.add(CharacterView("name 4", "nickname 4", "img 4", false))

        Mockito.`when`(getListCharactersUseCase.getRepositoryData()).thenReturn(
            DataState.Success(listCharacterView)
        )
    }

    private suspend fun failureResponse() {
        Mockito.`when`(getListCharactersUseCase.getRepositoryData()).thenReturn(
            DataState.Failure("Error when retrieving data from usecase")
        )
    }
}