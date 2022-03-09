package com.example.truelogicappchallenge.data.repository

import com.example.truelogicappchallenge.data.database.CharactersDao
import com.example.truelogicappchallenge.data.database.dto.CharacterCache
import com.example.truelogicappchallenge.data.network.ServiceApi
import com.example.truelogicappchallenge.data.network.responses.CharacterNetwork
import com.example.truelogicappchallenge.domain.CacheMapper
import com.example.truelogicappchallenge.domain.NetworkMapper
import com.example.truelogicappchallenge.domain.ResponseData
import com.example.truelogicappchallenge.domain.model.CharacterDomain
import com.example.truelogicappchallenge.domain.repository.CharactersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val api: ServiceApi,
    private val db: CharactersDao,
    private val networkMapper: NetworkMapper<CharacterNetwork, CharacterDomain>,
    private val cacheMapper: CacheMapper<CharacterCache, CharacterDomain>
    ): CharactersRepository {

    override suspend fun getListCharacters(): ResponseData<List<CharacterDomain>> {

        return withContext(Dispatchers.IO) {
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
                    ResponseData.Success(domainData)
                } else {
                    val domainData = dataFromCache.map {
                        val domainItem = cacheMapper.mapToDomainModel(it)
                        domainItem
                    }
                    ResponseData.Success(domainData)
                }

            } catch (e: IOException){
                val errorMessage = e.message.toString()
                ResponseData.Failure(errorMessage)
            }
        }

    }

    private suspend fun insertDataToCache(domainData: List<CharacterDomain>) {
        domainData.map {
            val cacheData = cacheMapper.mapFromDomainModel(it)
            db.insertCharacter(cacheData)
        }
    }

    override suspend fun getListFavorites(): List<CharacterCache> {
        return withContext(Dispatchers.IO) {
            db.getFavorites()
        }
    }

    override suspend fun handleFavorite(nameFavoriteItem: String, isFavorite: Boolean) {
        withContext(Dispatchers.IO){
            if(isFavorite){
                db.updateFavorite(nameFavoriteItem, false)
            } else {
                db.updateFavorite(nameFavoriteItem, true)
            }
        }
    }

}