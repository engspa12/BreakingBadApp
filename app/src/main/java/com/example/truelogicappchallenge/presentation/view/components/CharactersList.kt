package com.example.truelogicappchallenge.presentation.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.truelogicappchallenge.presentation.model.CharacterView

@Composable
fun CharactersListComponent(
    lazyState: LazyListState,
    list: List<CharacterView>?,
    onItemClicked: (CharacterView) -> Unit,
    onItemFavIconClicked: (Int) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
        state = lazyState
    ) {
        itemsIndexed(list ?: listOf()) { index, characterView ->
            CharacterItemComponent(
                characterView = CharacterView(
                    name = characterView.name,
                    nickname = characterView.nickname,
                    img = characterView.img,
                    isFavorite = characterView.isFavorite
                ),
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .fillMaxWidth(),
                {
                    onItemClicked(characterView)
                }
            ) {
                onItemFavIconClicked(index)
            }
        }
    }

}