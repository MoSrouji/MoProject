package com.example.myapplication.ui.auth.presentaiton.authentication.signin

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.myapplication.auth.domain.entities.Response
import com.example.myapplication.auth.domain.use_cases.authentication.AuthUseCases
import com.example.myapplication.ui.auth.presentaiton.base.BaseValidationViewModel
import com.example.myapplication.ui.auth.util.TextFieldType
import com.example.myapplication.ui.auth.validation.interfaces.TextFieldId
import com.example.myapplication.ui.auth.validation.state.ValidationState
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authenticatingUseCases: AuthUseCases
)
    : BaseValidationViewModel() {
    private val _signInState = mutableStateOf<Response<Boolean>>(Response.Success(false))
    val signInState = _signInState

    private var emailValidationState =
        ValidationState(id = SignInTextFieldId.EMAIL, type = TextFieldType.Email)

    private var passwordValidationState =
        ValidationState(id = SignInTextFieldId.PASSWORD, type = TextFieldType.Password)


    init {

        forms[SignInTextFieldId.EMAIL] = emailValidationState
        forms[SignInTextFieldId.PASSWORD] = passwordValidationState

    }




fun firebaseSignIn(email: String , password: String){
   viewModelScope.launch {
       authenticatingUseCases.firebaseSignIn(
           email = email ,
           password = password
       ).collect {
           _signInState.value = it

       }
   }
}

    fun restUser(){
        _signInState.value = Response.Success(false)
    }

}


enum class SignInTextFieldId : TextFieldId {
    EMAIL, PASSWORD
}