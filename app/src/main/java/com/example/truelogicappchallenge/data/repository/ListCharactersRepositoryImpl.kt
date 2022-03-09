package com.example.truelogicappchallenge.data.repository

import com.example.truelogicappchallenge.data.network.ServiceApi
import com.example.truelogicappchallenge.data.network.responses.CharacterNetwork
import com.example.truelogicappchallenge.domain.NetworkMapper
import com.example.truelogicappchallenge.domain.ResponseData
import com.example.truelogicappchallenge.domain.model.CharacterDomain
import com.example.truelogicappchallenge.domain.repository.ListCharactersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class ListCharactersRepositoryImpl @Inject constructor(
    private val api: ServiceApi,
    private val networkMapper: NetworkMapper<CharacterNetwork, CharacterDomain>
    ): ListCharactersRepository {

    override suspend fun getListCharacters(): ResponseData<List<CharacterDomain>> {

        return withContext(Dispatchers.IO) {
            try {
                val dataFromNetwork = api.getListCharacters(100)
                val domainData = dataFromNetwork.map {
                    val domainItem = networkMapper.mapToDomainModel(it)
                    domainItem
                }
                ResponseData.Success(domainData)
            } catch (e: IOException){
                val errorMessage = e.message.toString()
                ResponseData.Failure(errorMessage)
            }
        }

    }

}