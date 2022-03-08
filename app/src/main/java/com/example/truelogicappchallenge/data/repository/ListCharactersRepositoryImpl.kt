package com.example.truelogicappchallenge.data.repository

import com.example.truelogicappchallenge.data.network.ServiceApi
import com.example.truelogicappchallenge.data.network.responses.CharacterNetwork
import com.example.truelogicappchallenge.domain.ResponseData
import com.example.truelogicappchallenge.domain.repository.ListCharactersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class ListCharactersRepositoryImpl @Inject constructor(
    private val api: ServiceApi): ListCharactersRepository {

    override suspend fun getListCharacters(): ResponseData<List<CharacterNetwork>> {

        return withContext(Dispatchers.IO) {
            try {
                val dataFromNetwork = api.getListCharacters(100)
                ResponseData.Success(dataFromNetwork)
            } catch (e: IOException){
                val errorMessage = e.message.toString()
                ResponseData.Failure(errorMessage)

            }
        }

    }

}