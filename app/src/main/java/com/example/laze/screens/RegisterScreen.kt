package com.example.laze.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.laze.composables.AppLogo
import com.example.laze.composables.InputTextField
import com.example.laze.composables.SquareBox

@Composable
fun RegisterScreen(navController: NavController) {
    var signUpEmail = ""
    var signUpPass = ""
    var signUpCfmPass = ""
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
                    InputTextField(inputValue = signUpEmail, inputValueOnChange = {
                        signUpEmail = it
                    }, label = "Email", keyboard = KeyboardType.Email, isVisible = true)

                    InputTextField(inputValue = signUpPass, inputValueOnChange = {
                        signUpPass = it
                    }, label = "Password", keyboard = KeyboardType.Password, isVisible = false)

                    InputTextField(inputValue = signUpCfmPass, inputValueOnChange = {
                        signUpCfmPass = it
                    }, label = "Password", keyboard = KeyboardType.Password, isVisible = false)

                    Box(Modifier.height(10.dp))
                    Button(onClick = { /*TODO*/ }, Modifier.fillMaxWidth()) {
                        Text(text = "REGISTER")
                    }
                    Text(text = "Have an account? Login here", modifier = Modifier.clickable {
                        navController.popBackStack()
                    }, textAlign = TextAlign.Center)
                }

            }
        }
    }
}