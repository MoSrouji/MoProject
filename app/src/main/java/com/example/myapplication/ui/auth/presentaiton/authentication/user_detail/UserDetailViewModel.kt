package com.example.myapplication.ui.auth.presentaiton.authentication.user_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.auth.domain.entities.Response
import com.example.myapplication.auth.domain.entities.User
import com.example.myapplication.auth.domain.repositories.AuthRepository
import com.example.myapplication.auth.user_detail.domain.repository.UserDetailRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val userDetailRepo: UserDetailRepo,
    private val authRepository: AuthRepository


) : ViewModel() {

    private val _logOutState = MutableStateFlow<Response<Boolean>?>(null)
    val logOutState :StateFlow<Response<Boolean>?> = _logOutState

    private val _userState = mutableStateOf<User?>(null)
    val userState: State<User?> = _userState

    private val _loadingState = mutableStateOf(false)
    val loadingState: State<Boolean> = _loadingState

    private val _errorState = mutableStateOf<String?>(null)

    val errorState: State<String?> = _errorState


    init {
        loadUser()
    }

    fun loadUser() {
        viewModelScope.launch {
            _loadingState.value = true
            _errorState.value = null

            try {

                _userState.value = userDetailRepo.getCurrentUser()
            } catch (e: Exception) {
                _errorState.value = "Failed to load user details: ${e.message}"

            } finally {
                _loadingState.value = false
            }
        }
    }


    fun updateUserDetail(userDetail: User?) {
        viewModelScope.launch {
            _loadingState.value = true
            _errorState.value = null

            try {
                val success = userDetailRepo.updateUser(userDetail)

                if (success) {
                    _userState.value = userDetail
                }


            } catch (e: Exception) {
                _errorState.value = "Failed to update user details: ${e.message}"
            } finally {
                _loadingState.value = false
            }
        }
    }

    fun logout()= viewModelScope.launch {
        authRepository.firebaseSignOut()
            .collect {
                    response ->
                _logOutState.value = response
            }
    }

    fun resetLogoutState() {
        _logOutState.value = null
    }



}