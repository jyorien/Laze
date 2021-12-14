package com.example.laze.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.laze.composables.CurrentUserChatBubble
import com.example.laze.composables.OtherUserChatBubble
import com.example.laze.data.SentText
import com.google.firebase.auth.FirebaseAuth

@Composable
fun PrivateChatScreen(userId: String, navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val currentUserUid = auth.currentUser?.uid
    val chatList = listOf(
        SentText("Hello there~!", "asd"),
        SentText("Hello there~!", currentUserUid!!)


        )
    Scaffold {
        LazyColumn(verticalArrangement = Arrangement.Bottom, modifier = Modifier.fillMaxSize()) {
            items(chatList) { singleText ->
                if (singleText.senderUid == currentUserUid) {
                    CurrentUserChatBubble(currentUserText = singleText.text)
                } else {
                    OtherUserChatBubble(currentUserText = singleText.text)
                }
            }
        }
    }

}