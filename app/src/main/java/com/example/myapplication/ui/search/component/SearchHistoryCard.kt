package com.example.myapplication.ui.search.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.home.itemSpacing

@Composable
fun SearchHistoryCard(
    modifier: Modifier = Modifier,
    text: String,
    onSearchCardClick: Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(itemSpacing).height(50.dp)
        , onClick = {onSearchCardClick},
        shape = RectangleShape,


        ) {

        Text(
            modifier = Modifier.padding(15.dp),
            text = text,
            style = MaterialTheme.typography.bodyLarge ,
            fontWeight = FontWeight.Bold

        )
    }

}


@Composable
@Preview(showBackground = true)
fun SearchHistoryCardPreview() {
}