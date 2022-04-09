package com.example.truelogicappchallenge.domain.usecase

import androidx.test.filters.SmallTest
import com.example.truelogicappchallenge.domain.repository.CharactersRepository
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
@SmallTest
@RunWith(MockitoJUnitRunner::class)
class HandleFavoritesUseCaseImplTest{

    @Mock
    lateinit var charactersRepository: CharactersRepository

    lateinit var SUT: HandleFavoritesUseCase

    val captorName: ArgumentCaptor<String> = ArgumentCaptor.forClass(String::class.java)
    val captorIsFavorite: ArgumentCaptor<Boolean> = ArgumentCaptor.forClass(Boolean::class.java)

    @Suppress("UNCHECKED_CAST")
    private fun <T> capture(captor: ArgumentCaptor<T>): T = captor.capture()

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp(){
        SUT = HandleFavoritesUseCaseImpl(charactersRepository)
    }

    @Test
    fun updateFavoriteStatus_repositoryReceivesData() {
        return runTest {
            SUT.updateFavoriteStatus("name 2", true)

            verify(charactersRepository, times(1)).handleFavorite(capture(captorName), capture(captorIsFavorite))

            assertThat(captorName.value).isEqualTo("name 2")
            assertThat(captorIsFavorite.value).isEqualTo(true)
        }
    }
}