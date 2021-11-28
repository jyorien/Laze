package com.example.laze.tabs

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.laze.composables.ErrandItemLayout
import com.example.laze.data.Post

@Composable
fun PostedTab() {
    Log.d("hello","Posted Tab")
    val errandList = listOf<Post>(
        Post("Joey Lim", "I need some help pls help me walk my cat below my house", "asd"),
        Post("Joey Lim", "I need some help pls help me walk my cat below my house", "asd"),
        Post("Joey Lim", "I need some help pls help me walk my cat below my house", "asd"),
        Post("Joey Lim", "I need some help pls help me walk my cat below my house", "asd"),
        Post("Joey Lim", "I need some help pls help me walk my cat below my house", "asd"),
        Post("Joey Lim", "I need some help pls help me walk my cat below my house", "asd"),
        Post("Joey Lim", "I need some help pls help me walk my cat below my house", "asd"),
        Post("Joey Lim", "I need some help pls help me walk my cat below my house", "asd"),
    )
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn() {
            items(errandList) { currentPost ->
                ErrandItemLayout(post = currentPost, onClick = {
                    Log.d("hello","current post: $currentPost")
                })
            }
        }

    }
}