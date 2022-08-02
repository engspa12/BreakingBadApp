package com.example.truelogicappchallenge.data.network.mapper

import com.example.truelogicappchallenge.data.network.model.CharacterNetwork
import com.example.truelogicappchallenge.domain.model.CharacterDomain
import com.example.truelogicappchallenge.domain.util.NetworkMapper

class CharacterNetworkMapper: NetworkMapper<CharacterNetwork, CharacterDomain> {

    override fun mapToDomainModel(dto: CharacterNetwork): CharacterDomain {
        return CharacterDomain(
                dto.charId ?: 0, dto.name ?: "No name" , dto.nickname ?: "No nickname", dto.img ?: "", false
        )
    }

}