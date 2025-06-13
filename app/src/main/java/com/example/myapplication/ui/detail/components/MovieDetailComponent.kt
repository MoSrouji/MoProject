package com.example.myapplication.ui.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.home.components.MovieCard
import com.example.myapplication.ui.home.defaultPadding
import com.example.myapplication.ui.home.itemSpacing

@Composable
internal fun MovieDetailComponent(
    modifier: Modifier = Modifier,
    rating: Double,
    releaseDate: String,
) {
    Column(modifier) {
        MovieCard(
            modifier = Modifier.padding(horizontal = defaultPadding)
        ) {
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

                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(text = rating.toString())


                }
                Spacer(modifier = Modifier.width(itemSpacing))
                VerticalDivider(modifier = Modifier.height(16.dp))
                Spacer(modifier = Modifier.width(itemSpacing))
                Text(
                    text = releaseDate,
                    modifier = Modifier
                        .padding(6.dp),
                    maxLines = 1


                )

            }



        }

        Spacer(modifier= Modifier.padding(4.dp))
    }
}

