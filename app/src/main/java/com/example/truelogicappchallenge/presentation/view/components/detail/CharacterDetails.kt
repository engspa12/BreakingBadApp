package com.example.truelogicappchallenge.presentation.view.components.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.truelogicappchallenge.R
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun CharacterDetails(
    name: String,
    nickname: String,
    img: String,
    isFavorite: Boolean,
    buttonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
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
            shimmerParams = ShimmerParams(
                baseColor = MaterialTheme.colors.background,
                highlightColor = Color.Green,
                durationMillis = 350,
                dropOff = 0.65f,
                tilt = 20f
            ),
            error = painterResource(id = R.drawable.ic_person),
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
                    backgroundColor = if (isFavorite) Color.Red else Color.Blue
                ),
                onClick = {
                    buttonClick()
                }
            ) {
                Text(
                    text =
                    if (isFavorite)
                        stringResource(id = R.string.remove_from_favorites)
                    else
                        stringResource(id = R.string.add_to_favorites),
                    color = colorResource(id = R.color.white),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}