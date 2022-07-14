package com.example.truelogicappchallenge.presentation.view.compose.components.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.truelogicappchallenge.R
import com.example.truelogicappchallenge.presentation.model.CharacterView
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun CharacterItem(
    characterView: CharacterView,
    modifier: Modifier = Modifier,
    navCallback: () -> Unit,
    callbackItem: () -> Unit
){
    Row(
        modifier = modifier
            .clickable {
                navCallback()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        GlideImage(
            imageModel = characterView.img,
            requestOptions = {
                RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .centerCrop()
            },
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .requiredSize(width = 50.dp, height = 80.dp)
                .weight(1f)
        )
        Column(
            modifier = Modifier
                .weight(3f)
                .padding(start = 8.dp)
        ) {
            Text(
                text = characterView.name,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .fillMaxWidth()
                    .padding(start = 8.dp, top = 4.dp, bottom = 4.dp)
            )
            Text(
                text = characterView.nickname,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .background(MaterialTheme.colors.background)
                    .fillMaxWidth()
                    .padding(start = 8.dp, top = 4.dp, bottom = 4.dp)
            )
        }
        Image(
            painter = if(characterView.isFavorite) painterResource(id = R.drawable.ic_favorite) else painterResource(id = R.drawable.ic_no_favorite),
            modifier = Modifier
                .requiredSize(36.dp)
                .weight(1f)
                .padding(end = 4.dp)
                .clickable {
                    callbackItem()
                },
            contentDescription = null
        )

    }
}