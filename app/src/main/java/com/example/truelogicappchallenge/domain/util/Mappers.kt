package com.example.truelogicappchallenge.domain.util

import com.example.truelogicappchallenge.domain.model.CharacterDomain
import com.example.truelogicappchallenge.presentation.model.CharacterView

interface NetworkMapper<Dto, DomainModel> {
    fun mapToDomainModel(dto: Dto): DomainModel
}

interface CacheMapper<Dto, DomainModel> {
    fun mapToDomainModel(dto: Dto): DomainModel
    fun mapFromDomainModel(domainModel: DomainModel): Dto
}

fun CharacterDomain.toView(): CharacterView {
    val name = this.name
    val nickname = this.nickname
    val img = this.img
    val isFavorite = this.isFavorite

    return CharacterView(
        name, nickname, img, isFavorite
    )
}