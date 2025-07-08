package com.example.myapplication.ui.auth.presentaiton.authentication.user_detail



import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.auth.domain.entities.Response
import com.example.myapplication.ui.auth.presentaiton.authentication.user_detail.component.TopBar
import com.example.myapplication.ui.auth.presentaiton.authentication.user_detail.component.UserDetailContent

@Composable
fun UserDetailScreen(
    viewModel: UserDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onLogoutSuccess :()-> Unit

) {
    val user by viewModel.userState
    val isLoading by viewModel.loadingState
    val error by viewModel.errorState
    val logOutState by viewModel.logOutState.collectAsState()

    LaunchedEffect(logOutState){
        when(logOutState){
            is Response.Success->{
                onLogoutSuccess()
            viewModel.resetLogoutState()
            }
            is Response.Error -> {
                viewModel.resetLogoutState()
            }
            else -> {

            }
        }
    }


    Scaffold(
        topBar = {
            TopBar(
                onLogOutClick = {viewModel.logout()}

            )
        }
    ) { innerPadding ->
        UserDetailContent(
            user = user,
            isLoading = isLoading,
            error = error,
            onBackClick = onBackClick,
            onUpdateUser = { updatedUser ->
                viewModel.updateUserDetail(updatedUser)
            },
            onRetry = {},
            enableTextField = false,
            buttonString = "Update User Information" ,
            modifier = Modifier.padding(innerPadding) ,
            onLogOutClick = {
                viewModel.logout()
            }
        )
        if (logOutState is Response.Loading) {
            CircularProgressIndicator()
        }

    }
    }


