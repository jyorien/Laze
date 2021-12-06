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
import com.example.laze.composables.ErrandItemLayout
import com.example.laze.data.MainViewModel
import com.example.laze.data.OnError
import com.example.laze.data.OnSuccess
import com.example.laze.data.Post
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun PostedTab() {
    val viewModel = viewModel<MainViewModel>()
    Log.d("hello","Posted Tab")
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
                        })
                    }
                }

            }
        }

        is OnError -> {

        }
    }


}