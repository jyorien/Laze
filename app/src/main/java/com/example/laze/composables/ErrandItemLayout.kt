package com.example.laze.composables

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.laze.data.Post

@Composable
fun ErrandItemLayout(post: Post, onClick: (Post) -> Unit) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(130.dp)
        .padding(vertical = 4.dp)
        .border(width = 1.dp, color = Color.Black).clickable(enabled = true, onClick = {
            onClick(post)
        }),

    ) {
        Column(modifier = Modifier.padding(16.dp).fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
            Text(text = post.description)
            Text(text = "Assigned to @${post.username}", textAlign = TextAlign.End, modifier = Modifier.fillMaxWidth())

        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewErrand() {
    val post = Post(username = "Joey", description = "Looking for someone to help me with...", "asdf")
    ErrandItemLayout(post) {
        // onClick
    }
}