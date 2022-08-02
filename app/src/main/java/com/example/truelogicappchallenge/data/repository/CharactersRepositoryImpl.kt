package com.example.truelogicappchallenge.data.repository

import com.example.truelogicappchallenge.data.local.datasource.CharactersDao
import com.example.truelogicappchallenge.data.local.model.CharacterCache
import com.example.truelogicappchallenge.data.network.datasource.ServiceApi
import com.example.truelogicappchallenge.data.network.model.CharacterNetwork
import com.example.truelogicappchallenge.di.DispatchersModule
import com.example.truelogicappchallenge.domain.model.CharacterDomain
import com.example.truelogicappchallenge.domain.repository.CharactersRepository
import com.example.truelogicappchallenge.domain.util.CacheMapper
import com.example.truelogicappchallenge.domain.util.NetworkMapper
import com.example.truelogicappchallenge.util.ResultWrapper
import com.example.truelogicappchallenge.util.StringWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val api: ServiceApi,
    private val db: CharactersDao,
    private val networkMapper: NetworkMapper<CharacterNetwork, CharacterDomain>,
    private val cacheMapper: CacheMapper<CharacterCache, CharacterDomain>,
    @DispatchersModule.IODispatcher private val coroutineDispatcher: CoroutineDispatcher
    ): CharactersRepository {

    override suspend fun getListCharacters(): ResultWrapper<List<CharacterDomain>> {

        return withContext(coroutineDispatcher) {
            try {
                val dataFromCache = db.getFavorites()
                //If cache is empty, then use network data
                if(dataFromCache.isNullOrEmpty()){
                    val dataFromNetwork = api.getListCharacters(100)
                    val domainData = dataFromNetwork.map {
                        val domainItem = networkMapper.mapToDomainModel(it)
                        domainItem
                    }
                    insertDataToCache(domainData)
                    ResultWrapper.Success(domainData)
                } else {
                    val domainData = dataFromCache.map {
                        val domainItem = cacheMapper.mapToDomainModel(it)
                        domainItem
                    }
                    ResultWrapper.Success(domainData)
                }

            } catch (e: IOException){
                val errorMessage = e.message.toString()
                ResultWrapper.Failure(StringWrapper.SimpleString(errorMessage))
            }
        }

    }

    override suspend fun getCharacterDetails(name: String): Flow<CharacterDomain> {
        return withContext(coroutineDispatcher) {
            db.getCharacter(name).map {
                cacheMapper.mapToDomainModel(it)
            }
        }
    }

    private suspend fun insertDataToCache(domainData: List<CharacterDomain>) {
        domainData.map {
            val cacheData = cacheMapper.mapFromDomainModel(it)
            db.insertCharacter(cacheData)
        }
    }

    override suspend fun handleFavorite(nameFavoriteItem: String, isFavorite: Boolean) {
        withContext(coroutineDispatcher){
            db.updateFavorite(nameFavoriteItem, !isFavorite)
        }
    }

}