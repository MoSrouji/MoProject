package com.example.myapplication.ui.home

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.myapplication.movie.domain.models.Movie
import com.example.myapplication.navigation.NavAnimations
import com.example.myapplication.ui.components.LoadingView
import com.example.myapplication.ui.home.components.BodyContent
import com.example.myapplication.ui.home.components.TopContent
import kotlinx.coroutines.delay

val defaultPadding = 16.dp
val itemSpacing = 8.dp

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    onMovieClick: (id: Int) -> Unit,
    onDiscoverArrowClick: () -> Unit,
    onTradingArrowClick: () -> Unit,

) {
    val onBookMarkClick: (Movie) -> Unit = { movie ->
        homeViewModel.selectedMovie(movie)
    }
    val context = LocalContext.current

    val saveToWatchLaterLabel: String = "saveToWatchLater"
    var isAutoScrolling by remember {
        mutableStateOf(true)
    }

    val state by homeViewModel.homeState.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { state.discoverMovies.size }
    )
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()


    LaunchedEffect(key1 = pagerState.currentPage) {
        if (isDragged) {
            isAutoScrolling = false
        } else {
            isAutoScrolling = true
            delay(5000)
            with(pagerState) {
                val target = if (currentPage < state.discoverMovies.size - 1) currentPage + 1 else 0
                scrollToPage(target)
            }


        }
    }

    Box(modifier = modifier) {
        AnimatedVisibility(
            visible = state.error != null,
            enter = NavAnimations.slideInFromRight(),
            exit = NavAnimations.slideOutToLeft()
        ) {
            Text(
                text = state.error ?: "Unknown error",
                color = MaterialTheme.colorScheme.error,
                maxLines = 2
            )


        }
        AnimatedVisibility(
            visible = !state.isLoading && state.error == null,
            enter = NavAnimations.slideInFromRight(),
            exit = NavAnimations.slideOutToLeft()
        ) {

            BoxWithConstraints(modifier = modifier.fillMaxSize()) {
                val boxHeight = maxHeight
                val topItemHeight = boxHeight * .45f
                val bodyItemHeight = boxHeight * .55f
                HorizontalPager(
                    state = pagerState,
                    contentPadding = PaddingValues(defaultPadding),
                    pageSpacing = itemSpacing
                ) { page ->
                    if (isAutoScrolling) {
                        AnimatedContent(targetState = page) { index ->
                            TopContent(
                                modifier = Modifier
                                    .align(Alignment.TopCenter)
                                    .heightIn(min = topItemHeight),
                                movie = state.discoverMovies[index],
                                onMovieClick = {
                                    onMovieClick(it)
                                }
                            )


                        }
                    } else {
                        TopContent(
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .heightIn(min = topItemHeight),
                            movie = state.discoverMovies[page],
                            onMovieClick = {
                                onMovieClick(it)
                            }
                        )

                    }

                }


                BodyContent(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .heightIn(max = bodyItemHeight),
                    discoverMovies = state.discoverMovies,
                    trendingMovies = state.trendingMovies,
                    onMovieClick = onMovieClick,
                    onTradingArrowClick = onTradingArrowClick,
                    onDiscoverArrowClick = onDiscoverArrowClick,
                    onBookMarkClick = onBookMarkClick  )



            }
        }



    }
    LoadingView(isLoading = state.isLoading)
}









