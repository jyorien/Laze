package com.example.laze.tabs

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.laze.composables.ErrandItemLayout
import com.example.laze.data.MainViewModel
import com.example.laze.data.OnError
import com.example.laze.data.OnSuccess
import com.example.laze.data.Post
import com.example.laze.screens.UserPostDetailScreen
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun PostedTab() {
    val viewModel = viewModel<MainViewModel>()
    Log.d("hello","Posted Tab")

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Start") {
        composable("Detail/{data}", arguments = listOf(navArgument("data") {type = NavType.StringType})) { backStackEntry ->
            backStackEntry.arguments?.getString("data")?.let { data ->
                UserPostDetailScreen(data, navController)

            }
        }
        composable("Start") {
            when (val posts = viewModel.userPostsStateFlow.asStateFlow().collectAsState().value) {
                is OnSuccess -> {
                    val postsList = mutableListOf<Post>()
                    posts.querySnapshot?.documents?.forEach {
                        val post = Post(username = it["name"].toString(), description = it["description"].toString(), imageUrl = it["imageUrl"].toString())
                        postsList.add(post)
                    }
                    Column(modifier = Modifier.fillMaxSize()) {
                        LazyColumn() {
                            items(postsList) { currentPost ->
                                ErrandItemLayout(post = currentPost, onClick = {
                                    Log.d("hello","current post: $currentPost")
                                    val splitUrl = currentPost.imageUrl.split("?")
                                    val postDetails = "${currentPost.username}$${currentPost.description}$${splitUrl[0]}$${splitUrl[1]}"
                                    navController.navigate("Detail/$postDetails")
                                })
                            }
                        }

                    }
                }

                is OnError -> {

                }
            }
        }
    }



}