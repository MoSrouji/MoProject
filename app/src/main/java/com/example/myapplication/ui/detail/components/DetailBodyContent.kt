package com.example.myapplication.ui.detail.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.ui.graphics.Color
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WatchLater
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.myapplication.comment_review.domain.entities.MovieRating
import com.example.myapplication.movie.domain.models.Movie
import com.example.myapplication.movie_detail.domain.models.Review
import com.example.myapplication.movie_detail.domain.models.MovieDetail
import com.example.myapplication.ui.detail.DetailViewModel
import com.example.myapplication.ui.detail.MovieRatingUiState
import com.example.myapplication.ui.home.components.MovieCard
import com.example.myapplication.ui.home.components.MovieCoverImage
import com.example.myapplication.ui.home.defaultPadding
import com.example.myapplication.ui.home.itemSpacing

@Composable
fun DetailBodyContent(
    modifier: Modifier = Modifier,
    movieDetail: MovieDetail,
    movies: List<Movie>,
    isMovieLoading: Boolean,
    fetchMovies: () -> Unit,
    onMovieClick: (Int) -> Unit,
    onActorClick: (Int) -> Unit,
    onBookMarkClick: () -> Unit,
    onWatchedClick: () -> Unit,
    watchLaterLoading: Boolean,
    watchedLoading: Boolean,
    viewModel: DetailViewModel,
    ratingState: MovieRatingUiState,
    userRating: Float?,
    userReview: String?
) {
    LazyColumn(
        modifier = modifier
    ) {
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(defaultPadding)

                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            movieDetail.genreIds.forEachIndexed { index, genreText ->
                                Text(
                                    text = genreText,
                                    modifier = Modifier
                                        .padding(6.dp),
                                    maxLines = 1,
                                    style = MaterialTheme.typography.bodySmall

                                )
                                if (index != movieDetail.genreIds.lastIndex) {
                                    Text(
                                        text = " \u2022",
                                        style = MaterialTheme.typography.bodySmall

                                    )
                                }
                            }

                        }
                        Text(
                            text = movieDetail.runtime,
                            style = MaterialTheme.typography.bodySmall
                        )

                    }
                    Spacer(modifier = Modifier.height(itemSpacing))
                    Text(
                        text = movieDetail.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,

                        )
                    Spacer(modifier = Modifier.height(itemSpacing))
                    Text(
                        text = movieDetail.overview,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(itemSpacing))


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        SaveWatchButton(
                            onClick = onBookMarkClick,
                            loading = watchLaterLoading,
                            bgColor = Color.Black.copy(.5f),
                            icon = Icons.Default.BookmarkAdd
                        )
                        SaveWatchButton(
                            onClick = onWatchedClick,
                            loading = watchedLoading,
                            bgColor = Color.Black.copy(.5f),
                            icon = Icons.Default.WatchLater
                        )
                        ActionIcon.entries.forEachIndexed { index, actionIcon ->
                            ActionIconBtn(
                                icon = actionIcon.icon,
                                contentDescription = actionIcon.contentDescription,
                                bgColor = if (index == ActionIcon.entries.lastIndex) {
                                    MaterialTheme.colorScheme.primaryContainer
                                } else {
                                    Color.Black.copy(.5f)
                                }
                            )

                        }


                    }

                    Spacer(modifier = Modifier.height(itemSpacing))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = itemSpacing),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Cast & Crew",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(
                            onClick = { /*TODO*/ },
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = "More Cast & Crew"
                            )

                        }

                    }
                    Spacer(modifier = Modifier.height(itemSpacing))

                    LazyRow {
                        items(movieDetail.cast) {

                            ActorItem(
                                cast = it,
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { onActorClick(it.id) }

                            )
                            Spacer(modifier = Modifier.width(defaultPadding))

                        }
                    }
                    Spacer(modifier = Modifier.height(itemSpacing))

                    MovieInfoItem(
                        infoItem = movieDetail.language,
                        title = "Spoken Languages"
                    )
                    Spacer(modifier = Modifier.height(itemSpacing))

                    MovieInfoItem(
                        infoItem = movieDetail.productionCountry,
                        title = "Production Countries"
                    )
                    Spacer(modifier = Modifier.height(itemSpacing))

                    Text(
                        text = "Reviews",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(itemSpacing))
                    Review(reviews = movieDetail.reviews)
                    Spacer(modifier = Modifier.height(itemSpacing))

                    //Create a User Rate and Review Here :)
//
//                    MovieRatingScreen(
//                        uiState = ratingState,
//                        userRating = userRating,
//                        viewModel = viewModel
//
//                    )

                    MovieRatingReviewScreen(
                        viewModel = viewModel,
                        userRating = userRating,
                        userReview = userReview,
                        ratingState = ratingState,
                    )

                    MoreLikeThis(
                        fetchMovies = fetchMovies,
                        isMovieLoading = isMovieLoading,
                        movies = movies,
                        onMovieClick = onMovieClick,
                    )
                }

            }
        }

    }
}

private enum class ActionIcon(val icon: ImageVector, val contentDescription: String) {

    Share(icon = Icons.Default.Share, contentDescription = "Share"),
    Download(icon = Icons.Default.Download, contentDescription = "Download")

}

//Action Button Row Function
@Composable
private fun ActionIconBtn(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    contentDescription: String? = null,
    bgColor: Color = Color.Black.copy(.8f),
) {
    MovieCard(
        shapes = CircleShape,
        modifier = modifier
            .padding(4.dp),
        bgColor = bgColor
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.padding(4.dp)
        )
    }


}


//movie information function
@Composable
private fun MovieInfoItem(infoItem: List<String>, title: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(4.dp))
        infoItem.forEach {
            Text(
                text = it,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


//Save to Function
@Composable
private fun SaveWatchButton(
    onClick: () -> Unit,
    loading: Boolean,
    modifier: Modifier = Modifier,
    bgColor: Color = Color.Black.copy(.8f),
    icon: ImageVector


) {
    MovieCard(
        shapes = CircleShape,
        modifier = modifier
            .padding(4.dp)
            .size(35.dp),
        bgColor = bgColor
    ) {

        if (loading) {

            CircularProgressIndicator(modifier = Modifier.size(20.dp))
        } else {
            IconButton(
                onClick = onClick,

                ) {
                Icon(
                    imageVector = icon,
                    contentDescription = "Save To Watch Later",
                    modifier = Modifier.padding(4.dp)

                )

            }
        }
    }
}


//Review From The API Function

@Composable
private fun Review(
    modifier: Modifier = Modifier,
    reviews: List<Review>
) {
    val (viewMore, setViewMore) = remember {
        mutableStateOf(false)
    }

    //Show Only Three Reviews Or Less By Default
    val defaultReview =
        if (reviews.size > 3) reviews.take(3) else reviews

    //Show More When User Needs More Reviews
    val movieReviews = if (viewMore) reviews else defaultReview
    val btnText = if (viewMore) "Collapse" else "More ..."
    Column {
        movieReviews.forEach { review ->
            ReviewItem(review = review)
            Spacer(modifier = Modifier.height(itemSpacing))
            HorizontalDivider(modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(itemSpacing))

        }
        TextButton(onClick = { setViewMore(!viewMore) }) {
            Text(text = btnText)
        }

    }
}


//Get More Movie Like This Function

@Composable
fun MoreLikeThis(
    modifier: Modifier = Modifier,
    fetchMovies: () -> Unit,
    isMovieLoading: Boolean,
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit,
) {

    LaunchedEffect(key1 = true) {

        fetchMovies()

    }

    Column(modifier) {
        Text(
            text = "More Like This ",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,

            )
        LazyRow {
            item {
                AnimatedVisibility(visible = isMovieLoading) {
                    CircularProgressIndicator()
                }

            }

            items(
                movies
            ) {
                MovieCoverImage(
                    movie = it, onMovieClick = onMovieClick,
                    onBookMarkClick = {})
            }
        }

    }
}


@Composable
fun MovieRatingReviewScreen(
    viewModel: DetailViewModel,
    ratingState: MovieRatingUiState ,
    userRating: Float?,
    userReview: String?
) {

    var reviewText by remember { mutableStateOf(TextFieldValue(userReview ?: "")) }

    var selectedRating by remember {
        mutableStateOf<Float?>(null)
    }

    Column(
        modifier = Modifier
           // .padding(16.dp)
            .fillMaxWidth() // Changed from fillMaxSize to fillMaxWidth
    ) {


        when (ratingState) {
            is MovieRatingUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
Column {
                    CircularProgressIndicator()
                    Text("Loading")
                }
                }
            }

            is MovieRatingUiState.Error -> {
                Text(
                    text = (ratingState as MovieRatingUiState.Error).message,
                    color = MaterialTheme.colorScheme.error
                )
            }

            is MovieRatingUiState.Success -> {
                // Rating Section

                // Display existing reviews if available
                val movieRating = (ratingState as MovieRatingUiState.Success).movieRating

                Text(
                    text = " BATS_REVIEW :",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.padding(itemSpacing))

                RatingSummary(movieRating)
                Spacer(modifier = Modifier.padding(itemSpacing))
                if (!movieRating?.userReviews.isNullOrEmpty()) {
                    Column { // Changed from LazyColumn to regular Column since reviews are likely few

                        Spacer(modifier = Modifier.height(8.dp))

                        movieRating.userReviews.forEach { (userId, review) ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Text(
                                        text = "User ${userId.take(8)}...",
                                        style = MaterialTheme.typography.labelMedium
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = userRating.toString(),
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))

                                    Text(text = review)

                                    HorizontalDivider(thickness = 2.dp)

                                }
                            }
                        }




                    }

                }
                Column {
                    Text(
                        text = "Your Rating",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    RatingBar(
                        currentRating = selectedRating ?: userRating,
                        onRatingChanged = { selectedRating = it }
                    )
                    // Review Section
                    Text(
                        text = "Your Review",
                        style = MaterialTheme.typography.headlineSmall
                    )


                    OutlinedTextField(
                        value = reviewText,
                        onValueChange = { reviewText = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Write your review here...") },
                        maxLines = 5 ,
                    )


                    Button(
                        onClick = {


                            //viewModel.submitReview(reviewText.text)
                            selectedRating?.let { rating ->
                                viewModel.rateMovie(rating)
                                viewModel.submitReview(reviewText.text)

                            } ?: run {

                            }

                        },
                        modifier = Modifier.align(Alignment.End),
                        // enabled = reviewText.text.isNotBlank() && selectedRating != null
                    )
                    {
                        Text(
                            "Submit Review"
                        )
                    }
                }
                    }
                }


        }


        }




@Composable
fun RatingBar(
    currentRating: Float?,
    onRatingChanged: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        for (i in 1..5) {
            IconButton(
                onClick = { onRatingChanged(i.toFloat()) },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Rate $i stars",
                    tint = if (currentRating != null && i <= currentRating) Color.Yellow else Color.Gray
                )
            }
        }
    }

}


@Composable
private fun RatingSummary(rating: MovieRating?) {
    val averageRating = rating?.userRating?.values?.average()
    val ratingCount = rating?.userRating?.size



    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Card(modifier = Modifier.padding(10.dp).shadow(elevation = 16.dp) ,
            shape = RoundedCornerShape(defaultPadding),


        ) {
            Column(modifier = Modifier.padding(defaultPadding)) {

        Text(
            text = "Average Rating",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Text(
            text = "%.1f".format(averageRating?.times(2)),
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "Based on $ratingCount ratings",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
        }
        }
}
