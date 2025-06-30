package com.example.myapplication.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.R
import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.ui.theme.ColorGunmetal50
import com.example.myapplication.ui.theme.ColorVerdigris
import kotlinx.coroutines.delay
import java.security.Guard

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun LoadingView(modifier: Modifier = Modifier , isLoading: Boolean) {
    AnimatedVisibility(
        isLoading ,
        enter = fadeIn(),
    ) {
        BoxWithConstraints(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {}
                .background(ColorGunmetal50)
        ) {
            CircularProgressIndicator(modifier = Modifier.size(50.dp), color = ColorVerdigris)

        }
    }
}




            @Composable
            fun SplashScreen(
                modifier: Modifier = Modifier
,                onSplashEnd: () -> Unit
            ) {
                // Animation for alpha (opacity)
                val alpha = remember { Animatable(0f) }
                LaunchedEffect(Unit) {
                    alpha.animateTo(1f, animationSpec = tween(800))
                    delay(1000) // Show for 1 second
                    alpha.animateTo(0f, animationSpec = tween(800))
                    delay(300) // Wait for fade out to complete
                    onSplashEnd() // This triggers navigation
                }

                Box(
                    modifier = modifier
                        .fillMaxSize()
                        .background(ColorGunmetal50),
                    contentAlignment = Alignment.Center
                ) {
                    // Logo with animation
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground), // Replace with your logo
                        contentDescription = "App Logo",
                        modifier = Modifier
                            .size(150.dp)
                            .alpha(alpha.value)
                    )
                }

            }


@Preview
@Composable
fun LoadingViewPreview() {

}