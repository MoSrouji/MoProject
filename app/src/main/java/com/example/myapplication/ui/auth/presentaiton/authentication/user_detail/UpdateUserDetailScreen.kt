package com.example.myapplication.ui.auth.presentaiton.authentication.user_detail
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.ui.auth.presentaiton.authentication.user_detail.component.TopBar
import com.example.myapplication.ui.auth.presentaiton.authentication.user_detail.component.UserDetailContent

@Composable
fun UpdateUserDetailScreen(
    viewModel: UserDetailViewModel = hiltViewModel() ,
    onBackClick: () -> Unit ,
    onUpdateUser:()-> Unit

    ) {
    val user by viewModel.userState
    val isLoading by viewModel.loadingState
    val error by viewModel.errorState

    Scaffold(
        topBar = {
            TopBar(
                onLogOutClick = {}
            )
        }
    ) { innerPadding ->

        UserDetailContent(
            user = user,
            isLoading = isLoading,
            error = error,
            onBackClick = onBackClick,
            onUpdateUser = {onUpdateUser},
            onRetry = {},
            enableTextField = true,
            buttonString = "Save Changes" ,
            modifier = Modifier.padding(innerPadding)
        , onLogOutClick = {}
        )

    }
}