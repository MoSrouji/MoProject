package com.example.myapplication.ui.auth.validation.use_case

import com.example.myapplication.R
import com.example.myapplication.ui.auth.validation.interfaces.Validate
import com.example.myapplication.ui.auth.validation.state.ValidationResultState

class ValidateText : Validate {
    override fun execute(text: String): ValidationResultState {


        return if (text.isBlank()) {

            ValidationResultState(
                isValid = false,
                errorMessageId = R.string.the_field_can_not_be_blank
            )
        } else {

            ValidationResultState(isValid = true)
        }
    }
}