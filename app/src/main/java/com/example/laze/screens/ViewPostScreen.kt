package com.example.laze.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.laze.composables.Pager
import com.example.laze.data.MainViewModel
import com.example.laze.data.OnError
import com.example.laze.data.OnSuccess
import com.example.laze.data.Post
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun ViewPostScreen(viewModel: MainViewModel) {
    when (val posts = viewModel.postsStateFlow.asStateFlow().collectAsState().value) {
        is OnError -> {
            Text(text = "Posts couldn't load")
        }

        is OnSuccess -> {
            val postsList = mutableListOf<Post>()

            val data = posts.querySnapshot?.documents?.forEach { document ->
                val data = document.data
                data?.let {
                    val post = Post("Joye", data["description"].toString(), data["imageUrl"].toString())
                    postsList.add(post)
                }
            }
            Log.d("hello","success data $data")

                Pager(
        items = postsList,
        modifier = Modifier.fillMaxSize(), orientation = Orientation.Vertical,
        itemSpacing = 16.dp,
        contentFactory = { item ->
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item.imageUrl,
                    modifier = Modifier.padding(all = 16.dp),
                    style = MaterialTheme.typography.h6,
                )
            }
        }
    )
        }

    }

}