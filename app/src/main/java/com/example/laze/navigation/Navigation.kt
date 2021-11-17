package com.example.laze.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.laze.screens.AddPostScreen
import com.example.laze.screens.ChatListScreen
import com.example.laze.screens.UserPostListScreen
import com.example.laze.screens.ViewPostScreen

@Composable
fun BottomNavigationHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavigationItem.Home.route) {
        // bottom nav routes
        composable("ViewPostScreen") { ViewPostScreen() }
        composable("AddPostScreen") { AddPostScreen() }
        composable("ChatListScreen") { ChatListScreen() }
        composable("UserPostListScreen") { UserPostListScreen() }
    }
}