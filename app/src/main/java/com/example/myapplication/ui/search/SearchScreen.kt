package com.example.myapplication.ui.search


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapplication.navigation.NavAnimations
import com.example.myapplication.ui.components.LoadingView
import com.example.myapplication.ui.home.itemSpacing
import com.example.myapplication.ui.moviesScreenDiscoverAndTrending.MovieCardShow
import com.example.myapplication.ui.search.component.SearchHistoryCard


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    onMovieClick: (id: Int) -> Unit,
    onNavigateUP: () -> Unit,


    ) {
    val state by viewModel.searchState.collectAsStateWithLifecycle()


    Box(modifier = modifier.fillMaxWidth()) {
        AnimatedVisibility(
            state.error != null,
            modifier = Modifier.align(Alignment.Center),
            enter = NavAnimations.slideInFromRight(),
            exit = NavAnimations.slideOutToLeft()
        ) {
            Text(
                state.error ?: "Unknown error",
                color = MaterialTheme.colorScheme.error,
                maxLines = 2
            )
        }
        AnimatedVisibility(
            visible = !state.isLoading && state.error == null,
            enter = NavAnimations.slideInFromRight(),
            exit = NavAnimations.slideOutToLeft()
        ) {

            Column {
                SearchBar(
                    inputField = {
                        SearchBarDefaults.InputField(
                            query = viewModel.query,
                            onQueryChange = { newText ->
                                viewModel.onTextChanged(newText)
                            },
                            onSearch = {
                                viewModel.onBtnCLick()
                                viewModel.expanded = false
                            },
                            expanded = viewModel.expanded,
                            onExpandedChange = { expanded -> viewModel.onExpandedChanged(expanded) },

                            placeholder = { Text("Enter Movie Name ") },
                            leadingIcon = {
                                IconButton(onClick = { viewModel.onBtnCLick() }) {
                                    Icon(Icons.Default.Search, contentDescription = "Search")

                                }

                            },


                            )
                    },
                    expanded = viewModel.expanded,
                    onExpandedChange = { expanded -> viewModel.onExpandedChanged(expanded) },
                    shape = CircleShape,
                    modifier = Modifier.padding(5.dp)
                ) {

                    LazyColumn( modifier = Modifier ,
                        horizontalAlignment = Alignment.CenterHorizontally ,
                        verticalArrangement = Arrangement.SpaceBetween) {

                        items(viewModel.browseHistory) {

                            SearchHistoryCard(
                                modifier = Modifier.fillMaxWidth() ,
                                text = it ,
                                onSearchCardClick = viewModel.onTextChanged(it)
                            )
                        }

                    }
                }

                Text(
                    text = "The Result : ",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )


                LazyColumn(modifier = Modifier.padding(vertical = itemSpacing)) {
                    items(state.searchMovies) {
                        MovieCardShow(
                            movie = it,
                            onMovieClick = onMovieClick,
                        )
                    }
                }



            }
        }
        LoadingView(isLoading = state.isLoading)
    }
}


