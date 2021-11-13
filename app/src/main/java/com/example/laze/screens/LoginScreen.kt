package com.example.laze.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.laze.R
import com.example.laze.composables.AppLogo
import com.example.laze.composables.InputTextField
import com.example.laze.composables.SquareBox

@Composable
fun LoginScreen() {
    var inputEmail by remember { mutableStateOf("") }
    var inputPassword by remember { mutableStateOf("")}
    Scaffold {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
        ) {
            AppLogo()
            SquareBox {
                Column {
                    InputTextField(inputValue = inputEmail, inputValueOnChange = {
                        inputEmail = it
                    }, label = "Email")

                    InputTextField(inputValue = inputPassword, inputValueOnChange = {
                        inputPassword = it
                    }, label = "Password")
                }

            }
        }
    }

}

@Preview
@Composable
fun PreviewLoginScreen() {
    LoginScreen()
}