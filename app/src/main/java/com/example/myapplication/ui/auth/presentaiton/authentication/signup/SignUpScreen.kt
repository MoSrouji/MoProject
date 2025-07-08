package com.example.myapplication.ui.auth.presentaiton.authentication.signup


import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.auth.domain.entities.Response
import com.example.myapplication.ui.auth.util.TextFieldType
import com.example.myapplication.ui.auth.validation.event.ValidationEvent
import com.example.myapplication.ui.auth.validation.event.ValidationResultEvent
import com.example.myapplication.ui.auth.widget.button.AuthenticationButton
import com.example.myapplication.ui.auth.widget.loading.LoadingScreen
import com.example.myapplication.ui.auth.widget.textfield.AuthenticationTextField
import com.example.myapplication.ui.theme.ColorGunmetal
import com.example.myapplication.ui.theme.IbarraNovaBoldPlatinum18
import com.example.myapplication.R
import com.example.myapplication.ui.theme.IbarraNovaBoldPlatinum25


@Composable
fun SignUpScreen(navController: NavController ,
                 navToHomeScreen: ()-> Unit

) {


    val viewModel: SignUpViewModel = hiltViewModel()

    val signUpState = viewModel.signUpState.value

    val context = LocalContext.current



    LaunchedEffect(key1 = context) {


        viewModel.validationEvent.collect { event ->


            when (event) {
                ValidationResultEvent.Success -> {


                    viewModel.firebaseSingUp(
                        fullName = viewModel.forms[SignUpTextFieldId.FULL_NAME]!!.text,
                        email = viewModel.forms[SignUpTextFieldId.EMAIL]!!.text.trim(),
                        password = viewModel.forms[SignUpTextFieldId.PASSWORD]!!.text
                    )
                }

            }

        }
    }


    LaunchedEffect(signUpState) {

        when (signUpState) {

            is Response.Success -> {
                if (signUpState.data) {
                    Toast.makeText(context, R.string.sign_up_successfully, Toast.LENGTH_LONG).show()
                    navToHomeScreen()

                }
            }

            is Response.Error -> {
                Toast.makeText(context, signUpState.massage, Toast.LENGTH_LONG).show()
            }

            is Response.Loading ->{
               // Toast.makeText(context, "Loading ", Toast.LENGTH_LONG).show()

            }
        }
    }



Box(modifier = Modifier.fillMaxSize().background(ColorGunmetal))
{
    Column(

        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .background(ColorGunmetal)

    ) {

        Spacer(modifier = Modifier.height(40.dp))

        //Back Image
        Image(
            painter = painterResource(id = R.drawable.ic_back),
            modifier = Modifier
                .align(Alignment.Start)
                .width(40.dp)
                .height(30.dp)
                .clickable {
                    navController.popBackStack()
                },
            contentDescription = "back"
        )

        Spacer(modifier = Modifier.height(20.dp))

        //Create Account Text
        Text(
            text = stringResource(id = R.string.create_account),
            style = IbarraNovaBoldPlatinum25
        )

        Spacer(modifier = Modifier.height(5.dp))

        //Please Fill Text
        Text(
            text = stringResource(id = R.string.please_sign_in_to_continue),
            style = IbarraNovaBoldPlatinum18
        )

        Spacer(modifier = Modifier.height(40.dp))

        //FullName TextField
        AuthenticationTextField(
            modifier = Modifier.fillMaxWidth(0.85f),
            state = viewModel.forms[SignUpTextFieldId.FULL_NAME]!!,
            hint = R.string.full_name,
            onValueChange = {
                viewModel.onEvent(

                    ValidationEvent.TextFieldValueChange(
                        viewModel.forms[SignUpTextFieldId.FULL_NAME]!!.copy(
                            text = it
                        )
                    )
                )
            },
            type = TextFieldType.Text
        )
        Spacer(modifier = Modifier.height(20.dp))

        //Email TextField
        AuthenticationTextField(
            modifier = Modifier.fillMaxWidth(0.85f),
            state = viewModel.forms[SignUpTextFieldId.EMAIL]!!,
            hint = R.string.email,
            onValueChange = {

                viewModel.onEvent(
                    ValidationEvent.TextFieldValueChange(

                        viewModel.forms[SignUpTextFieldId.EMAIL]!!.copy(text = it)
                    )
                )

            },
            type = TextFieldType.Email
        )
        Spacer(modifier = Modifier.height(20.dp))


        //Password TextField
        AuthenticationTextField(
            modifier = Modifier.fillMaxWidth(0.85f),
            state = viewModel.forms[SignUpTextFieldId.PASSWORD]!!,
            hint = R.string.password,
            onValueChange = {


                viewModel.onEvent(
                    ValidationEvent.TextFieldValueChange(
                        viewModel.forms[SignUpTextFieldId.PASSWORD]!!.copy(text = it)
                    )
                )
            },
            type = TextFieldType.Password
        )
        Spacer(modifier = Modifier.height(40.dp))


        AuthenticationButton(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .height(50.dp), textId = R.string.sign_up,
            onClick = {

                viewModel.onEvent(ValidationEvent.Submit)


            }
        )


    }


    if (signUpState == Response.Loading)
        LoadingScreen()
}
}



@Composable
@Preview
fun SignUpPreview() {

    //   SignUpScreen()
}


