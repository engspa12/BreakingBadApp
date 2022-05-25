package com.example.truelogicappchallenge.presentation.view.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.truelogicappchallenge.presentation.viewmodel.ListCharactersViewModel
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun DetailScreen(
    name: String,
    nickname: String,
    img: String,
    isFavorite: Boolean,
    modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top
    ) {
        GlideImage(
            imageModel = img,
            requestOptions = {
                RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            },
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .requiredSize(width = 140.dp, height = 200.dp)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 20.dp)
        ) {
            Text(
                text = name,
                textAlign = TextAlign.Start,
                fontSize = 26.sp
            )
            Text(
                text = nickname,
                textAlign = TextAlign.Start,
                fontSize = 20.sp
            )
            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if(isFavorite) Color.Red else Color.Blue
                ),
                onClick = {
                    //viewmodel.updateFavoriteStatus()
                }
            ) {
                Text(
                    text = if (isFavorite) "Remove from favorites" else "Add to favorites",
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

