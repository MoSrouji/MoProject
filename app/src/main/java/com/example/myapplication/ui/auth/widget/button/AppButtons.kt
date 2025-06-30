package com.example.myapplication.ui.auth.widget.button

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.ColorVerdigris
import com.example.myapplication.ui.theme.IbarraNovaSemiBoldGraniteGray



@Composable
fun AuthenticationButton(
    modifier: Modifier,
    @StringRes textId: Int,
    onClick: () -> Unit,
) {

    CustomButton(
        modifier = modifier,
        color = ColorVerdigris,
        onClick = onClick,
        textId = textId,
        textStyle = IbarraNovaSemiBoldGraniteGray,
        cornerRadius = 25.dp
    )
}


