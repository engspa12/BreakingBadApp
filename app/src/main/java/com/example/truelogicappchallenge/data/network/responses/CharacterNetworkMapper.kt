package com.example.truelogicappchallenge.data.network.responses

import com.example.truelogicappchallenge.domain.NetworkMapper
import com.example.truelogicappchallenge.domain.model.CharacterDomain

class CharacterNetworkMapper: NetworkMapper<CharacterNetwork,CharacterDomain> {

    override fun mapToDomainModel(dto: CharacterNetwork): CharacterDomain {
        return CharacterDomain(
                dto.charId ?: 0, dto.name ?: "No name" , dto.nickname ?: "No nickname", dto.img ?: "", false
        )
    }

}