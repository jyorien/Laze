package com.example.laze.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.laze.data.MainViewModel
import com.example.laze.navigation.BottomNavigationBar
import com.example.laze.navigation.BottomNavigationHost
import com.google.accompanist.pager.ExperimentalPagerApi

// TODO Implement Bottom Nav and Screens
@ExperimentalPagerApi
@Composable
fun HomeScreen(viewModel: MainViewModel) {
    val bottomNavController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(bottomNavController) }
    ) {
        Box(modifier = Modifier.padding(bottom = it.calculateBottomPadding())) {
            BottomNavigationHost(bottomNavController, viewModel)
        }
    }
}