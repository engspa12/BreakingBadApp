package com.example.truelogicappchallenge.data.helper

interface NetworkMapper<Dto, DomainModel> {

    fun mapToDomainModel(dto: Dto): DomainModel
}

interface CacheMapper<Dto, DomainModel> {

    fun mapToDomainModel(dto: Dto): DomainModel
    fun mapFromDomainModel(domainModel: DomainModel): Dto
}