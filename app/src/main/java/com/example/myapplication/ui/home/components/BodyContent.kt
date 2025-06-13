package com.example.myapplication.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapplication.movie.domain.models.Movie
import com.example.myapplication.ui.home.itemSpacing

@Composable
fun BodyContent(modifier: Modifier = Modifier,
                discoverMovies: List<Movie>,
                trendingMovies: List<Movie>,
                onMovieClick:(id: Int) -> Unit,
                onDiscoverArrowClick:() ->Unit,
                onTradingArrowClick:()->Unit
              ) {
    LazyColumn (modifier = modifier) {
        item {
        Card(modifier= Modifier.fillMaxWidth()){

                Row(modifier= Modifier
                    .fillMaxWidth()
                    .padding(itemSpacing) ,
                    horizontalArrangement = Arrangement.SpaceBetween ,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Discover Movies " ,
                        style = MaterialTheme.typography.titleLarge ,
                        fontWeight = FontWeight.Bold,
                    )
                    IconButton(onClick = onDiscoverArrowClick){

                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward ,
                            contentDescription = "More discover movies"

                        )
                    }
                }

                LazyRow(modifier = Modifier.padding(horizontal = itemSpacing)) {
                    items (discoverMovies) {
                        MovieCoverImage(movie = it ,
                            onMovieClick =onMovieClick ,)
                    }
                }


                Row(modifier= Modifier
                    .fillMaxWidth()
                    .padding(itemSpacing) ,
                    horizontalArrangement = Arrangement.SpaceBetween ,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Trending now" ,
                        style = MaterialTheme.typography.titleLarge ,
                        fontWeight = FontWeight.Bold,
                    )
                    IconButton(onClick = onTradingArrowClick) {

                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward ,
                            contentDescription = "Trending now"

                        )
                    }
                }

                LazyRow(modifier = Modifier.padding(horizontal = itemSpacing)) {
                    items (trendingMovies) {
                        MovieCoverImage(movie = it ,
                            onMovieClick =onMovieClick ,)
                    }
                }

                Spacer(modifier= Modifier.padding(horizontal = 100.dp))


            }
        }
        }

        }

