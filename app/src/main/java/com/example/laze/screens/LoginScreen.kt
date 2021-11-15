package com.example.laze.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.laze.R
import com.example.laze.composables.AppLogo
import com.example.laze.composables.InputTextField
import com.example.laze.composables.SquareBox

@Composable
fun LoginScreen(navController: NavController?) {
    var inputEmail by rememberSaveable { mutableStateOf("") }
    var inputPassword by rememberSaveable { mutableStateOf("") }
    Scaffold {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
        ) {
            AppLogo()
            SquareBox {
                Column(horizontalAlignment = Alignment.CenterHorizontally,modifier = Modifier.padding(10.dp)) {
                    Text(
                        "Login",
                        textAlign = TextAlign.Center,
                        fontSize = 40.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Box(Modifier.height(10.dp))
                    InputTextField(inputValue = inputEmail, inputValueOnChange = {
                        inputEmail = it
                    }, label = "Email", keyboard = KeyboardType.Email, isVisible = true)

                    InputTextField(inputValue = inputPassword, inputValueOnChange = {
                        inputPassword = it
                    }, label = "Password", keyboard = KeyboardType.Password, isVisible = false)
                    Box(Modifier.height(10.dp))
                    Button(onClick = { /*TODO*/ }, Modifier.fillMaxWidth()) {
                        Text(text = "LOGIN")
                    }
                    Text(text = "No account? Sign up here", modifier = Modifier.clickable {
                        navController!!.navigate("RegisterScreen")
                    }, textAlign = TextAlign.Center)
                }

            }
        }
    }

}

@Preview
@Composable
fun PreviewLoginScreen() {
    LoginScreen(null)
}