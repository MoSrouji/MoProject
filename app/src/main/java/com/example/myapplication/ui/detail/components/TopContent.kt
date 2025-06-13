package com.example.myapplication.ui.detail.components


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myapplication.R
import com.example.myapplication.movie_detail.domain.models.MovieDetail
import com.example.myapplication.utils.K


@Composable
fun DetailTopContent(
    modifier: Modifier = Modifier,
    movieDetail: MovieDetail
) {
    val imageRequest = ImageRequest.Builder(LocalContext.current)
        .data("${K.BASE_IMAGE_URL}${movieDetail.posterPath}")
        .crossfade(true)
        .build()


    Box(modifier = modifier.fillMaxWidth()) {
        AsyncImage(
            model = imageRequest,
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop,
            onError = {
                it.result.throwable.printStackTrace()
            },
            placeholder = painterResource(id = R.drawable.bg_image_movie)
        )

        MovieDetailComponent(
            rating = movieDetail.voteAverage,
            releaseDate = movieDetail.releaseDate ,
            modifier = Modifier
                .align(Alignment.BottomStart)
        )
    }

}


