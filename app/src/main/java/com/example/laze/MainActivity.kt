package com.example.laze

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.laze.screens.HomeScreen
import com.example.laze.screens.LoginScreen
import com.example.laze.screens.RegisterScreen
import com.example.laze.ui.theme.LazeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        setContent {
            LazeTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "LoginScreen") {
                    composable("LoginScreen") { LoginScreen(navController) }
                    composable("RegisterScreen") { RegisterScreen(navController) }
                    composable("HomeScreen") { HomeScreen(navController)}
                }
            }
        }
    }
}



