package com.example.laze.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.laze.data.MainViewModel
import com.example.laze.navigation.BottomNavigationBar
import com.google.accompanist.pager.ExperimentalPagerApi

// TODO Implement Bottom Nav and Screens
@ExperimentalPagerApi
@Composable
fun HomeScreen(viewModel: MainViewModel, navController: NavHostController) {
//    val bottomNavController = rememberNavController()
    Scaffold(

    ) {
        Box(modifier = Modifier.padding(bottom = it.calculateBottomPadding())) {
//            BottomNavigationHost(navController, viewModel)
        }
    }
}