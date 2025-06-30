package com.example.myapplication.ui.auth.validation.event

import com.example.myapplication.ui.auth.validation.state.ValidationState


sealed class ValidationEvent{

    object Submit: ValidationEvent()
    data class TextFieldValueChange(val state:ValidationState):ValidationEvent()
}
