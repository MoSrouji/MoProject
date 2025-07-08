package com.example.myapplication.utils

import android.provider.CalendarContract
import androidx.compose.foundation.Image
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.ui.theme.ColorGunmetal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertDialogSample(
    title : String ,
    text : String ,
    onConfirmClick:()-> Unit ,



) {
    val openDialog = remember {
        mutableStateOf(true)
    }

    if (openDialog.value) {

        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = title)
            },
            text = {
                Text(
                    text = text
                )
            },

            confirmButton = {
                TextButton(onClick = {}) { Text("Confirm") }
            },
            dismissButton = {
                TextButton(onClick = {
                    openDialog.value=false
                }) { Text("Dismiss") }
            },
            icon = {
                Image(painterResource(R.drawable.batman__streamline_cyber),
                    contentDescription = null)
            }
            , 
            tonalElevation = 5.dp ,



        )
    }

}

@Preview
@Composable
fun AlertDialogPreview() {
    AlertDialogSample(
        title = "SignIn" ,
        text = " You Cant Write an Review Before Sign up Please Sign up And Try Again " ,
        onConfirmClick = {} ,
        )

}