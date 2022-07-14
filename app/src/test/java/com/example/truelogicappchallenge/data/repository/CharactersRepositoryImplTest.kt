package com.example.truelogicappchallenge.data.repository

import androidx.test.filters.SmallTest
import com.example.truelogicappchallenge.data.database.CharactersDao
import com.example.truelogicappchallenge.data.database.dto.CharacterCache
import com.example.truelogicappchallenge.data.database.dto.CharacterCacheMapper
import com.example.truelogicappchallenge.data.network.ServiceApi
import com.example.truelogicappchallenge.data.network.response.CharacterNetwork
import com.example.truelogicappchallenge.data.network.response.CharacterNetworkMapper
import com.example.truelogicappchallenge.domain.CacheMapper
import com.example.truelogicappchallenge.domain.NetworkMapper
import com.example.truelogicappchallenge.data.helper.ResultData
import com.example.truelogicappchallenge.domain.model.CharacterDomain
import com.example.truelogicappchallenge.domain.repository.CharactersRepository
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
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule
import org.mockito.kotlin.any
import org.mockito.kotlin.never
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import java.io.IOException


@OptIn(ExperimentalCoroutinesApi::class)
@SmallTest
@RunWith(MockitoJUnitRunner::class)
class CharactersRepositoryImplTest {

    @Mock
    lateinit var serviceApi: ServiceApi

    @Mock
    lateinit var dao: CharactersDao

    lateinit var SUT: CharactersRepository
    lateinit var networkMapper: NetworkMapper<CharacterNetwork, CharacterDomain>
    lateinit var cacheMapper: CacheMapper<CharacterCache, CharacterDomain>

    val captorName: ArgumentCaptor<String> = ArgumentCaptor.forClass(String::class.java)
    val captorIsFavorite: ArgumentCaptor<Boolean> = ArgumentCaptor.forClass(Boolean::class.java)

    @Suppress("UNCHECKED_CAST")
    private fun <T> capture(captor: ArgumentCaptor<T>): T = captor.capture()

    @Rule
    @JvmField
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Before
    fun setUp(){
        networkMapper = CharacterNetworkMapper()
        cacheMapper = CharacterCacheMapper()
        SUT = CharactersRepositoryImpl(serviceApi, dao, networkMapper, cacheMapper, UnconfinedTestDispatcher())
    }

    @Test
    fun getListCharacters_successfulResponse_cacheEmptyNetworkIsCalled(){
        return runTest {
            val job = launch {
                cacheResponse(cacheIsFull = false)
                networkResponse(withError = false)
            }

            job.join()

            val listCharactersDomain = SUT.getListCharacters() as ResultData.Success

            verify(dao, times(1)).getFavorites()
            verify(dao, times(2)).insertCharacter(any())
            verify(dao, never()).updateFavorite(any(), any())
            verify(serviceApi, times(1)).getListCharacters(any())

            assertThat(listCharactersDomain.value[0].name).isEqualTo("name network 0")
            assertThat(listCharactersDomain.value[0].nickname).isEqualTo("nickname network 0")
            assertThat(listCharactersDomain.value[1].name).isEqualTo("name network 1")
            assertThat(listCharactersDomain.value[1].nickname).isEqualTo("nickname network 1")
        }
    }

    @Test
    fun getListCharacters_successfulResponse_cacheFullNetworkIsNotCalled(){
        return runTest {
            val job = launch {
                cacheResponse(cacheIsFull = true)
                networkResponse(withError = false)
            }

            job.join()

            val listCharactersDomain = SUT.getListCharacters() as ResultData.Success

            verify(dao, times(1)).getFavorites()
            verify(dao, never()).insertCharacter(any())
            verify(dao, never()).updateFavorite(any(), any())
            verify(serviceApi, never()).getListCharacters(any())

            assertThat(listCharactersDomain.value[0].name).isEqualTo("name cache 0")
            assertThat(listCharactersDomain.value[0].nickname).isEqualTo("nickname cache 0")
            assertThat(listCharactersDomain.value[1].name).isEqualTo("name cache 1")
            assertThat(listCharactersDomain.value[1].nickname).isEqualTo("nickname cache 1")
        }
    }

    @Test
    fun getListCharacters_failureResponse_errorIsReturned(){
        return runTest {
            val job = launch {
                cacheResponse(cacheIsFull = false)
                networkResponse(withError = true)
            }

            job.join()

            val response = SUT.getListCharacters() as ResultData.Failure

            verify(dao, times(1)).getFavorites()
            verify(dao, never()).insertCharacter(any())
            verify(dao, never()).updateFavorite(any(), any())
            verify(serviceApi, times(1)).getListCharacters(any())

            assertThat(response.errorMessage).isEqualTo("An error occurred in networkResponse")
        }
    }

    @Test
    fun handleFavorite_cacheIsCalled_itemIsUpdatedWithNewValue() {

        return runTest {
            val job = launch {
                cacheResponse(cacheIsFull = false)
                networkResponse(withError = false)
            }

            job.join()

            SUT.handleFavorite("name 4", true)

            verify(dao, never()).getFavorites()
            verify(dao, never()).insertCharacter(any())
            verify(dao, times(1)).updateFavorite(capture(captorName), capture(captorIsFavorite))
            verify(serviceApi, never()).getListCharacters(any())

            assertThat(captorName.value).isEqualTo("name 4")
            assertThat(captorIsFavorite.value).isEqualTo(false)
        }

    }

    private suspend fun cacheResponse(cacheIsFull: Boolean) {

        if(cacheIsFull){
            val listCharactersCache = ArrayList<CharacterCache>()

            listCharactersCache.add(CharacterCache(0,"name cache 0","nickname cache 0","image cache 0",true))
            listCharactersCache.add(CharacterCache(1,"name cache 1","nickname cache 1","image cache 1",false))

            Mockito.`when`(dao.getFavorites()).thenReturn(
                listCharactersCache
            )
        } else {
            Mockito.`when`(dao.getFavorites()).thenReturn(
                ArrayList()
            )
        }

    }

    private suspend fun networkResponse(withError: Boolean) {

        if(withError) {
            Mockito.`when`(serviceApi.getListCharacters(any())).thenAnswer {
                throw IOException("An error occurred in networkResponse")
            }
        } else {
            val listCharactersNetwork = ArrayList<CharacterNetwork>()

            listCharactersNetwork.add(CharacterNetwork(0,"name network 0","birthday network 0", ArrayList(),
                "image network 0","status network 0","nickname network 0", ArrayList(),"portrayed network 0","category network 0", ArrayList()))
            listCharactersNetwork.add(CharacterNetwork(1,"name network 1","birthday network 1", ArrayList(),
                "image network 1","status network 1","nickname network 1", ArrayList(),"portrayed network 1","category network 1", ArrayList()))

            Mockito.`when`(serviceApi.getListCharacters(any())).thenReturn(
                listCharactersNetwork
            )
        }

    }

}