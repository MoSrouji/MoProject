package com.example.myapplication.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow


@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun TopAppBar(
    onSearchClick:() -> Unit ,
    onUserButtonClick:() -> Unit ,
    onMenuClick:()-> Unit
) {
            TopAppBar(
                title = { Text("Bat-Rate", maxLines = 1, overflow = TextOverflow.Ellipsis) },
                navigationIcon = {
                    IconButton(onClick = onMenuClick) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description",
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onSearchClick) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search",
                        )
                    }
                    IconButton(onClick = onUserButtonClick) {
            Icon(
                imageVector = Icons.Filled.PersonPin,
                contentDescription = "User Profile",
            )
        }
                },
            )

     
            }
 





//    TopAppBar() {
//
//        IconButton(onClick = { /* doSomething() */ }) {
//            Icon(
//                imageVector = Icons.Filled.Menu,
//                contentDescription = "Localized description",
//            )
//        }
//        Text(
//
//            text = "Bat-Rate", maxLines = 1, overflow = TextOverflow.Ellipsis,
//            style = MaterialTheme.typography.titleLarge,
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier.clickable {
//
//            }
//        )
//
//
//        // RowScope here, so these icons will be placed horizontally
//        IconButton(onClick = { /* doSomething() */ }) {
//            Icon(
//                imageVector = Icons.Filled.Search,
//                contentDescription = "Localized description",
//            )
//        }
//        IconButton(onClick = { /* doSomething() */ }) {
//            Icon(
//                imageVector = Icons.Filled.PersonPin,
//                contentDescription = "Localized description",
//            )
//        }
//    }
//
//}


