package com.example.myapplication.ui.auth.presentaiton.authentication.signup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.myapplication.auth.domain.entities.Response
import com.example.myapplication.auth.domain.use_cases.authentication.AuthUseCases
import com.example.myapplication.ui.auth.presentaiton.base.BaseValidationViewModel
import com.example.myapplication.ui.auth.util.TextFieldType
import com.example.myapplication.ui.auth.validation.interfaces.TextFieldId
import com.example.myapplication.ui.auth.validation.state.ValidationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authenticatingUseCases: AuthUseCases
): BaseValidationViewModel() {

    private val _signUpState = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val signUpState: State<Response<Boolean>> = _signUpState
    private var fullNameValidationState =
        ValidationState(type = TextFieldType.Text, id = SignUpTextFieldId.FULL_NAME)

    private var emailValidationState =
        ValidationState(type = TextFieldType.Email, id = SignUpTextFieldId.EMAIL)

    private var passwordValidationState =
        ValidationState(type = TextFieldType.Password, id = SignUpTextFieldId.PASSWORD)


    init {
        forms[SignUpTextFieldId.FULL_NAME] = fullNameValidationState
        forms[SignUpTextFieldId.EMAIL] = emailValidationState
        forms[SignUpTextFieldId.PASSWORD] = passwordValidationState
    }

    fun firebaseSingUp(
        fullName: String,
        email: String,
        password: String
    ) {

     viewModelScope.launch {
        authenticatingUseCases.firebaseSignUp(
            fullName = fullName,
            email = email,
            password = password
        ).collect {
            _signUpState.value = it
        }
    }
    }


}
enum class SignUpTextFieldId : TextFieldId {
    FULL_NAME, EMAIL, PASSWORD
}
