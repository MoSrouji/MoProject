package com.example.myapplication.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle

@Composable
fun CollapsibleText(
    modifier: Modifier = Modifier,
    collapsibleMaxLines: Int = 3,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    text: String
) {


    var isExpended by remember {
        mutableStateOf(false)
    }

    val annotationString = buildAnnotatedString {
        if (!isExpended) {
            withStyle(SpanStyle(color = Color.Blue)) {
                append("See more")
            }
        } else {
            withStyle(SpanStyle(color = Color.Blue)) {
                append("See less")
            }
        }
    }

    Column {
        Text(
            text = text,
            modifier = Modifier
                .clickable { isExpended = !isExpended },
            maxLines = if (isExpended) Int.MAX_VALUE else collapsibleMaxLines,
            style = style,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = annotationString,
            modifier = Modifier
                .clickable { isExpended = !isExpended },
            overflow = TextOverflow.Ellipsis,
            style = style
        )


    }


}