package com.example.laze.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.laze.composables.CurrentUserChatBubble
import com.example.laze.composables.InputTextField
import com.example.laze.composables.OtherUserChatBubble
import com.example.laze.data.SentText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun PrivateChatScreen(userId: String, navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val chatList = listOf(
        SentText("Hello there~!", "asd"),
        SentText("Hello there~!", userId)
    )
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    var textToSend by rememberSaveable { mutableStateOf("") }

    Scaffold {
        Column() {
            LazyColumn(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.fillMaxWidth().height(550.dp)
            ) {

                items(chatList) { singleText ->
                    if (singleText.senderUid == userId) {
                        CurrentUserChatBubble(currentUserText = singleText.text)
                    } else {
                        OtherUserChatBubble(currentUserText = singleText.text)
                    }
                }
            }
            Row(modifier = Modifier.fillMaxSize().border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(2.dp)), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
                InputTextField(
                    inputValue = textToSend,
                    inputValueOnChange = { textToSend = it },
                    label = "Text",
                    isVisible = true,
                    isError = false,
                    focusManager = focusManager,
                    focusRequester = focusRequester
                )
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "SEND")
                }
            }
        }


    }

}