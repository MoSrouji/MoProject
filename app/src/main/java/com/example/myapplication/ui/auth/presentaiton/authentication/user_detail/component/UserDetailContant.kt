package com.example.myapplication.ui.auth.presentaiton.authentication.user_detail.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.auth.domain.entities.User



@Composable
fun UserDetailContent(
    user: User?,
    isLoading: Boolean,
    error: String?,
    onUpdateUser: (User) -> Unit,
    onBackClick: () -> Unit,
    onRetry: () -> Unit,
    enableTextField: Boolean,
    buttonString: String,
    modifier: Modifier ,
    onLogOutClick:()-> Unit
) {
    var name by remember { mutableStateOf(user?.fullName ?: "") }
    var email by remember { mutableStateOf(user?.email ?: "") }

    LaunchedEffect(user) {
        name = user?.fullName ?: ""
        email = user?.email ?: ""
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            return
        }

        if (error != null) {
            ErrorMessage(error = error, onRetry = onRetry)
            return
        }

        if (user == null) {
            Text("No user data available", style = MaterialTheme.typography.bodyMedium)
            return
        }


        // User Details

        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center
        ) {


            Card(
                modifier = Modifier,
                shape = CircleShape,


                ) {

                Image(
                    painterResource(R.drawable.baseline_person_24),
                    contentDescription = "Person Image ",
                    modifier = Modifier.size(150.dp)
                )
            }
        }

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Full Name", style = MaterialTheme.typography.bodyMedium) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enableTextField,

            )


        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email", style = MaterialTheme.typography.bodyMedium) },
            modifier = Modifier.fillMaxWidth(),
            enabled = enableTextField
        )




        Spacer(modifier = Modifier.padding())

        Button(
            onClick = {
                val updatedUser = user?.copy(
                    fullName = name,
                    email = email
                )
                onUpdateUser(updatedUser!!)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(buttonString)
        }
        Button(
            onClick = {} ,
            modifier = Modifier.fillMaxWidth().height(56.dp).padding(horizontal = 50.dp)
        ) {
            Text("Change Password")

        }
        Button(
            onClick = onLogOutClick ,
            modifier = Modifier.fillMaxWidth().height(56.dp).padding(horizontal = 90.dp)
        ) {
            Text("Log Out ")

        }
    }
}


