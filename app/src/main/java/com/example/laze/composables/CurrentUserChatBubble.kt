package com.example.laze.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CurrentUserChatBubble(currentUserText: String) {
    Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        Column(
            modifier = Modifier
                .background(
                    color = Color(5, 96, 98),
                    shape = RoundedCornerShape(
                        topStart = 5.dp,
                        topEnd = 5.dp,
                        bottomEnd = 0.dp,
                        bottomStart = 5.dp
                    )
                )
                .padding(horizontal = 10.dp, vertical = 5.dp)
                .widthIn(0.dp, 250.dp)
        ) {
            Text(currentUserText, color = Color.White)
        }

    }
}

@Preview
@Composable
fun Preview_current_user_bubble() {
    CurrentUserChatBubble("hello")
}