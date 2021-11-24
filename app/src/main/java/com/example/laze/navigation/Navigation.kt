package com.example.laze.navigation

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.laze.screens.*

@Composable
fun BottomNavigationHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavigationItem.Home.route) {
        // bottom nav routes
        composable("ViewPostScreen") { ViewPostScreen() }
        composable("AddPostScreen") { AddPostScreen(navController) }
        composable("ChatListScreen") { ChatListScreen() }
        composable("UserPostListScreen") { UserPostListScreen() }

        // secondary routes
        composable(
            "CapturedImageScreen/{img}",
            arguments = listOf(navArgument("img") { type = NavType.StringType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("img")?.let { data ->
                Log.d("hello","data $data")
                CapturedImageScreen(data, navController)

            }
        }
    }
}