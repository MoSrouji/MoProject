package com.example.myapplication.ui.auth.widget.textfield

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.ColorAmericanPurple
import com.example.myapplication.ui.theme.IbarraNovaNormalGray14
import com.example.myapplication.ui.theme.IbarraNovaSemiBoldPlatinum16
import com.example.myapplication.ui.auth.util.TextFieldType
import com.example.myapplication.ui.auth.validation.state.ValidationState


@Composable
fun AuthenticationTextField(
    modifier: Modifier,
    state: ValidationState,
    @StringRes hint: Int,
    onValueChange: (String) -> Unit,
    type: TextFieldType

) {

    CustomTextField(
        modifier = modifier,
        state = state,
        hint = hint,
        onValueChange = onValueChange,
        textStyle = IbarraNovaSemiBoldPlatinum16,
        hintTextStyle = IbarraNovaNormalGray14,
        color = ColorAmericanPurple,
        cornerRadius = 15.dp,
        type = type
    )

}