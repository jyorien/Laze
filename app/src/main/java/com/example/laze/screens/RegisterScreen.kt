package com.example.laze.screens

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.laze.composables.AppLogo
import com.example.laze.composables.InputTextField
import com.example.laze.composables.SquareBox
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RegisterScreen(navController: NavController) {
    val mAuth = FirebaseAuth.getInstance()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    var signUpEmail by remember { mutableStateOf("") }
    var signUpPass by remember { mutableStateOf("") }
    var signUpCfmPass by remember { mutableStateOf("") }
    var isEmailError by remember { mutableStateOf(false) }
    var isPassError by remember { mutableStateOf(false) }
    var isCfmPassError by remember { mutableStateOf(false) }
    var isGeneralError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Scaffold {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
        ) {
            AppLogo()
            SquareBox {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text(
                        "Register",
                        textAlign = TextAlign.Center,
                        fontSize = 40.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Box(Modifier.height(10.dp))
                    InputTextField(
                        inputValue = signUpEmail,
                        inputValueOnChange = {
                            signUpEmail = it
                        },
                        label = "Email",
                        isVisible = true,
                        isEmailError,
                        focusManager,
                        focusRequester
                    )

                    InputTextField(
                        inputValue = signUpPass,
                        inputValueOnChange = {
                            signUpPass = it
                        },
                        label = "Password",
                        isVisible = false,
                        isPassError,
                        focusManager,
                        focusRequester
                    )

                    InputTextField(
                        inputValue = signUpCfmPass,
                        inputValueOnChange = {
                            signUpCfmPass = it
                        },
                        label = "Confirm Password",
                        isVisible = false,
                        isCfmPassError,
                        focusManager,
                        focusRequester
                    )

                    Box(Modifier.height(10.dp))
                    Button(onClick = {
                        isEmailError = false
                        isPassError = false
                        isCfmPassError = false
                        isGeneralError = false
                        if (signUpEmail.isEmpty() || signUpPass.isEmpty() || signUpCfmPass.isEmpty()) {
                            isGeneralError = true
                            errorMessage = "Fields cannot be empty"
                            return@Button
                        } else if (!Patterns.EMAIL_ADDRESS.matcher(signUpEmail).matches()) {
                            isEmailError = true
                            errorMessage = "Email is badly formatted!"
                            return@Button
                        } else if (signUpPass.length < 6) {
                            isPassError = true
                            errorMessage = "Password must have 6 or more characters!"
                            return@Button
                        } else if (signUpPass != signUpCfmPass) {
                            isPassError = true
                            isCfmPassError = true
                            errorMessage = "Passwords do not match!"
                            return@Button
                        }

                        mAuth.createUserWithEmailAndPassword(signUpEmail, signUpPass)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(
                                        context,
                                        "User has been created!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    signUpEmail = ""
                                    signUpPass = ""
                                    signUpCfmPass = ""
                                } else {
                                    task.exception?.let {
                                        errorMessage = it.localizedMessage!!
                                    }
                                }
                            }


                    }, Modifier.fillMaxWidth()) {
                        Text(text = "REGISTER")
                    }
                    if (isEmailError || isPassError || isCfmPassError || isGeneralError) {
                        Text(errorMessage, color = Color.Red)
                    }
                    Text(text = "Have an account? Login here", modifier = Modifier.clickable {
                        navController.popBackStack()
                    }, textAlign = TextAlign.Center)
                }

            }
        }
    }
}