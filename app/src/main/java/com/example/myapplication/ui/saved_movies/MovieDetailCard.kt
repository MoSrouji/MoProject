package com.example.myapplication.ui.saved_movies


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myapplication.R
import com.example.myapplication.movie.domain.models.Movie
import com.example.myapplication.movie_detail.domain.models.MovieDetail
import com.example.myapplication.ui.home.components.MovieCard
import com.example.myapplication.ui.home.itemSpacing
import com.example.myapplication.utils.K

@Composable
fun MovieDetailCard(
    modifier: Modifier = Modifier,
    movie: MovieDetail,
    onMovieClick: (Int) -> Unit

) {
    val imageRequest = ImageRequest.Builder(LocalContext.current)
        .data("${K.BASE_IMAGE_URL}${movie.posterPath}")
        .crossfade(true)
        .build()
    Card {
        Row(modifier = modifier.padding(itemSpacing)) {
            Box(
                modifier = modifier
                    .size(width = 120.dp, height = 200.dp)
                    .clickable { onMovieClick(movie.id) }) {
                AsyncImage(
                    model = imageRequest,
                    contentDescription = null,
                    modifier = Modifier
                        .matchParentSize()
                        .clip(MaterialTheme.shapes.medium)
                        .shadow(elevation = 4.dp),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(R.drawable.bg_image_movie)
                )
                MovieCard(
                    shapes = CircleShape,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.BookmarkAdd,
                        contentDescription = "Bookmark",
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier
                        .padding(4.dp)


                )


                HorizontalDivider(
                    modifier = Modifier.padding(2.dp),
                    thickness = 1.dp,
                    color = Color.Black
                )

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
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
                        Spacer(modifier = Modifier.padding(2.dp))
                        Text(text = movie.voteAverage.toString())
                    }
                    Spacer(modifier = Modifier.width(itemSpacing))
                    VerticalDivider(modifier = Modifier.height(16.dp))
                    Spacer(modifier = Modifier.width(itemSpacing))
                    Text(
                        text = movie.releaseDate,
                        modifier = Modifier
                            .padding(6.dp),
                        maxLines = 1


                    )
                }

                Spacer(modifier = modifier.padding(2.dp))
                Card(
                    modifier = modifier
                        .padding(2.dp)
                        .shadow(elevation = 4.dp),
                    shape = RoundedCornerShape(corner = MaterialTheme.shapes.small.topStart) ,
                    onClick = { }
                ) {
                    Text(
                        text = movie.overview,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 3, modifier = modifier.padding(4.dp)
                    )
                }
                Spacer(modifier = modifier.padding(2.dp))
                Row {
                    Card(
                        onClick = {}, modifier = modifier
                            .padding(2.dp)
                    ) {
                        Row(
                            modifier = modifier,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.StarBorder,
                                contentDescription = "Rating",
                                modifier = modifier.size(20.dp)
                            )
                            Text(
                                text = "Rate",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Blue.copy(alpha = .5f),
                                fontWeight = FontWeight.Bold,
                                modifier = modifier.padding(
                                    start = 4.dp,
                                    end = 4.dp,
                                    top = 4.dp,
                                    bottom = 4.dp
                                )

                            )
                        }}
                    Spacer(modifier = modifier.padding(itemSpacing))
                    Card(onClick = {}, modifier = modifier) {
                        Row(
                            modifier = modifier,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.RemoveRedEye,
                                contentDescription = "Rating",
                                modifier = modifier.padding((4.dp))
                                    .size(20.dp)

                            )
                            Text(
                                text = "Mark as watched",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Blue.copy(alpha = .5f),
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }
                }
            }
        }
    }
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 16.dp),
        thickness = 1.dp,
        color = Color.Black
    )
}

