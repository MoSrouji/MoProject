package com.example.myapplication.ui.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.myapplication.movie_detail.domain.models.Review
import com.example.myapplication.ui.components.CollapsibleText
import com.example.myapplication.ui.home.itemSpacing
import kotlin.math.round

@Composable
fun ReviewItem(
    modifier: Modifier = Modifier ,
    review: Review

) {
    Column (
        modifier = modifier
    ) {

        val nameAnnotatedString = buildAnnotatedString {
            append(review.author)
            append(" \u2022 ")
            append(review.createdAt)
        }
        val ratingAnnotatedString = buildAnnotatedString {

            //Apply bold Style To "6/"
            pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
            append(round(review.rating).toString())
            pop() //End Small Styling


            //Apply Small Font Size To "10"

            pushStyle(SpanStyle(fontSize = 10.sp))
            append("10")
            pop() // End Small Styling

        }


        Text(
            text = nameAnnotatedString,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold

        )

        Spacer(modifier = Modifier.height(itemSpacing))

        CollapsibleText(
            text = review.content,
            style = MaterialTheme.typography.bodyLarge,
        )

        Spacer(modifier = Modifier.height(itemSpacing))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Rating"
            )
            Text(text = ratingAnnotatedString , style = MaterialTheme.typography.bodySmall)
        }


    }

}
