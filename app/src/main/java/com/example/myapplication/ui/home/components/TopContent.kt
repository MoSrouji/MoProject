package com.example.myapplication.ui.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.example.myapplication.movie.domain.models.Movie
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myapplication.R
import com.example.myapplication.ui.home.defaultPadding
import com.example.myapplication.ui.home.itemSpacing
import com.example.myapplication.utils.K

@Composable
fun TopContent(
    modifier: Modifier = Modifier,
    movie: Movie,
    onMovieClick: (id: Int) -> Unit
) {
    val imgRequest = ImageRequest.Builder(LocalContext.current)
        .data("${K.BASE_IMAGE_URL}${movie.posterPath}")
        .crossfade(true)
        .build()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onMovieClick(movie.id) }) {
        AsyncImage(
            model = imgRequest,
            contentDescription = null,
            modifier = Modifier.matchParentSize()
                .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop,

            onError = {
                it.result.throwable.printStackTrace()
            }, placeholder = painterResource(id = R.drawable.bg_image_movie)
        )
        MovieDetail(
            modifier = Modifier.align(Alignment.BottomStart)
                .padding(bottom = 20.dp),
            rating = movie.voteAverage,
            title = movie.title,
            genre = movie.genreIds)
    }
}


@Composable
fun MovieDetail(
    modifier: Modifier = Modifier,
    rating: Double,
    title: String,
    genre: List<String>
) {
    Column(modifier = modifier.padding(defaultPadding)) {

        MovieCard {
            Row(
                modifier = Modifier.padding(4.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Rating",
                    tint = Color.Yellow

                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = rating.toString())


            }
        }
        Spacer(modifier = Modifier.height(itemSpacing))
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(itemSpacing))
        MovieCard {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                genre.forEachIndexed { index,genreText->
                    if (index!=0){
                        VerticalDivider(modifier= Modifier.height(16.dp))

                    }
                    Text(
                        text = genreText,
                        modifier = Modifier
                            .padding(6.dp)
                            .weight(1f),
                        maxLines = 1

                    )
                    if (index !=genre.lastIndex){
                        VerticalDivider(modifier= Modifier.height(16.dp))
                    }

                }


            }
        }



    }
}


@Preview(showBackground = true , backgroundColor = 0xFFF00F0F )
@Composable
private fun MovieDetailPreview() {
    MovieDetail(rating = 8.5, title = "Avengers",
        genre = listOf("Action", "Adventure","Fantasy"))
}