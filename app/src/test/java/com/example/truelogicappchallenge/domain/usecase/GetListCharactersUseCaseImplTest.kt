package com.example.truelogicappchallenge.domain.usecase

import androidx.test.filters.SmallTest
import com.example.truelogicappchallenge.domain.helper.DataState
import com.example.truelogicappchallenge.data.helper.ResponseData
import com.example.truelogicappchallenge.domain.model.CharacterDomain
import com.example.truelogicappchallenge.domain.repository.CharactersRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
@SmallTest
@RunWith(MockitoJUnitRunner::class)
class GetListCharactersUseCaseImplTest {

    @Mock
    lateinit var characterRepository: CharactersRepository

    lateinit var SUT: GetListCharactersUseCase

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp() {
        SUT = GetListCharactersUseCaseImpl(characterRepository)
    }

    @Test
    fun getRepositoryData_successfulResponse_listIsReturned(){
        return runTest {

            val job = launch {
                successfulResponse()
            }

            job.join()

            val response = SUT.getRepositoryData() as DataState.Success

            verify(characterRepository, times(1)).getListCharacters()
            assertThat(response.value[0].name).isEqualTo("name 3")
            assertThat(response.value[1].name).isEqualTo("name 5")
            assertThat(response.value[2].name).isEqualTo("name 1")
            assertThat(response.value[3].name).isEqualTo("name 2")
            assertThat(response.value[4].name).isEqualTo("name 4")

        }
    }

    @Test
    fun getRepositoryData_failureResponse_errorMessageIsReturned(){
        return runTest {

            val job = launch {
                failureResponse()
            }

            job.join()

            val response = SUT.getRepositoryData() as DataState.Failure

            verify(characterRepository, times(1)).getListCharacters()
            assertThat(response.errorMessage).isEqualTo("An error occurred")
        }
    }

    private suspend fun successfulResponse() {

        val listCharactersDomain = arrayListOf<CharacterDomain>()

        listCharactersDomain.add(CharacterDomain(1,"name 1","nickname 1","image 1", isFavorite = false))
        listCharactersDomain.add(CharacterDomain(2,"name 2","nickname 2","image 2", isFavorite = false))
        listCharactersDomain.add(CharacterDomain(3,"name 3","nickname 3","image 3", isFavorite = true))
        listCharactersDomain.add(CharacterDomain(4,"name 4","nickname 4","image 4", isFavorite = false))
        listCharactersDomain.add(CharacterDomain(5,"name 5","nickname 5","image 5", isFavorite = true))

        Mockito.`when`(characterRepository.getListCharacters())
            .thenReturn(
                ResponseData.Success(listCharactersDomain)
            )
    }

    private suspend fun failureResponse() {
        Mockito.`when`(characterRepository.getListCharacters())
            .thenReturn(
                ResponseData.Failure("An error occurred")
            )
    }

}