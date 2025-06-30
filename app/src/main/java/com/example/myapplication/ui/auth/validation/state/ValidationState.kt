package com.example.myapplication.ui.auth.validation.state

import androidx.annotation.StringRes
import com.example.myapplication.ui.auth.util.TextFieldType
import com.example.myapplication.ui.auth.validation.interfaces.TextFieldId

data class ValidationState(
    var text:String = "",
    val type:TextFieldType = TextFieldType.Text,
    val id:TextFieldId,
    val isRequired:Boolean = true,
    var hasError:Boolean = true,
    @StringRes val errorMessageId: Int? = null,
)
