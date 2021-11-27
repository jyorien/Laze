package com.example.laze.screens

import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.laze.data.MainViewModel
import com.example.laze.navigation.BottomNavigationBar
import com.example.laze.navigation.BottomNavigationHost

// TODO Implement Bottom Nav and Screens
@Composable
fun HomeScreen(viewModel: MainViewModel) {
    val bottomNavController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(bottomNavController) }
    ) {
        BottomNavigationHost(bottomNavController, viewModel)
    }
}