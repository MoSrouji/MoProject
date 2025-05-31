package com.example.myapplication.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LoadingView(modifier: Modifier = Modifier , isLoading: Boolean) {
    AnimatedVisibility(
        isLoading ,
        enter = fadeIn() + expandVertically(),
    ) {
        Box(modifier = modifier.fillMaxSize()){
            LinearProgressIndicator(modifier = Modifier.fillMaxSize())
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.primary.copy(alpha = .3f)
            ) {  }
        }
    }
}


@Preview
@Composable
fun LoadingViewPreview() {
    LoadingView(isLoading = true)
}