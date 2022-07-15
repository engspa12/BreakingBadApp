package com.example.truelogicappchallenge.data.local.mapper

import com.example.truelogicappchallenge.data.helper.CacheMapper
import com.example.truelogicappchallenge.data.local.model.CharacterCache
import com.example.truelogicappchallenge.domain.model.CharacterDomain

class CharacterCacheMapper: CacheMapper<CharacterCache, CharacterDomain> {
    override fun mapToDomainModel(dto: CharacterCache): CharacterDomain {
        return CharacterDomain(
            dto.id,
            dto.characterName,
            dto.characterNickname,
            dto.urlImg,
            dto.isFavorite
        )
    }

    override fun mapFromDomainModel(domainModel: CharacterDomain): CharacterCache {
        return CharacterCache(
            id = domainModel.id,
            characterName = domainModel.name,
            characterNickname = domainModel.nickname,
            urlImg = domainModel.img,
            isFavorite = domainModel.isFavorite
        )
    }

}