package com.example.laze

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.laze.data.MainViewModel
import com.example.laze.navigation.BottomNavigationBar
import com.example.laze.navigation.bottomNavigationGraph
import com.example.laze.screens.*
import com.example.laze.ui.theme.LazeTheme
import com.google.accompanist.pager.ExperimentalPagerApi

const val HOME_ROUTE = "home_route"
class MainActivity : ComponentActivity() {
    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setContent {
            LazeTheme {
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = { if (viewModel.hasBottomNav.collectAsState().value) BottomNavigationBar(navController) }
                ) {
                    Box(modifier = Modifier.padding(bottom = it.calculateBottomPadding())) {
                    NavHost(navController = navController, startDestination = "LoginScreen") {

                        composable("LoginScreen") {
                            viewModel.hasBottomNav.value = false
                            LoginScreen(navController)
                        }
                        composable("RegisterScreen") {
                            viewModel.hasBottomNav.value = false
                            RegisterScreen(navController)
                        }
                        bottomNavigationGraph(navController = navController, viewModel = viewModel)

                    }
                }
                }
            }
        }
    }
}



