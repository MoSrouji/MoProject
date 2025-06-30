package com.example.myapplication.ui.auth.validation.interfaces

import com.example.myapplication.ui.auth.validation.state.ValidationResultState

interface Validate {

    fun execute(text: String): ValidationResultState
}

