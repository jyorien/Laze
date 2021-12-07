package com.example.laze.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.laze.data.Post

@Composable
fun ErrandItemLayout(post: Post, onClick: (Post) -> Unit, onDelete: (Post) -> Unit) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(230.dp)
        .padding(vertical = 4.dp)
        .border(width = 1.dp, color = Color.Black)
        .clickable(enabled = true, onClick = {
            onClick(post)
        }),

    ) {
        Column(modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
            Text(text = post.description, Modifier.height(100.dp).fillMaxWidth())
            Text(text = "Assigned to @${post.username}", textAlign = TextAlign.End, modifier = Modifier.fillMaxWidth())
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete button", modifier = Modifier.align(Alignment.End).clickable { onDelete(post) })

        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewErrand() {
    val post = Post(username = "Joey", description = "Looking for someone to help me with...", "asdf")
    ErrandItemLayout(post, {}, {})
}