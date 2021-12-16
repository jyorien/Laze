package com.example.laze.navigation

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.laze.HOME_ROUTE
import com.example.laze.data.MainViewModel
import com.example.laze.screens.*
import com.google.accompanist.pager.ExperimentalPagerApi

@ExperimentalPagerApi

fun NavGraphBuilder.bottomNavigationGraph(navController: NavHostController, viewModel: MainViewModel) {
    navigation(startDestination = NavigationItem.Home.route, route = HOME_ROUTE) {
        // bottom nav routes
        composable("ViewPostScreen") { ViewPostScreen(viewModel, navController) }
        composable("AddPostScreen") { AddPostScreen(navController) }
        composable("ChatListScreen") { ChatListScreen(viewModel, navController) }
        composable("UserPostListScreen") { UserPostListScreen() }

        // secondary routes
        composable(
            "CapturedImageScreen/{img}",
            arguments = listOf(navArgument("img") { type = NavType.StringType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("img")?.let { data ->
                Log.d("hello", "data $data")
                CapturedImageScreen(data, navController)

            }
        }
        composable("PrivateChatScreen/{userId}/{rawImageRef}", arguments = listOf(navArgument("userId") {type = NavType.StringType})) { backStackEntry ->
            backStackEntry.arguments?.getString("userId")?.let { data ->
                backStackEntry.arguments?.getString("rawImageRef")?.let { data2 ->
                    PrivateChatScreen(data, data2, navController, viewModel)

                }
            }
        }
        composable("ListPrivateChatScreen/{userId}/{arr0}/{arr1}/{arr2}") { backStackEntry ->
            backStackEntry.arguments?.getString("userId")?.let { userId ->
                backStackEntry.arguments?.getString("arr0")?.let { arr0 ->
                    backStackEntry.arguments?.getString("arr1")?.let { arr1 ->
                        backStackEntry.arguments?.getString("arr2")?.let { arr2 ->
                            ListPrivateChatScreen(
                                viewModel = viewModel,
                                userId = userId,
                                arr0 = arr0,
                                arr1 = arr1,
                                arr2 = arr2
                            )

                        }

                    }

                }

            }
        }
    }
}