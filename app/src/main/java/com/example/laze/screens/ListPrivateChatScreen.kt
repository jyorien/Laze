package com.example.laze.screens

import android.util.Log
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
import com.example.laze.composables.CurrentUserChatBubble
import com.example.laze.composables.InputTextField
import com.example.laze.composables.OtherUserChatBubble
import com.example.laze.data.MainViewModel
import com.example.laze.data.OnChatError
import com.example.laze.data.OnChatSuccess
import com.example.laze.data.SentText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun ListPrivateChatScreen(viewModel: MainViewModel, userId: String, arr0: String, arr1: String, arr2: String) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    var textToSend by rememberSaveable { mutableStateOf("") }
    val firestore = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    val currentUserId = auth.currentUser?.uid
    val collectRef = "$arr0?$arr1?$arr2"

    Scaffold {
        when (val documents = viewModel.messagesList.asStateFlow().collectAsState().value) {
            is OnChatError -> {
                Text("Oops, something went wrong.")
                Log.d("hello","wrong")
            }

            is OnChatSuccess -> {
                val messages = mutableListOf<SentText>()
                documents.data.forEach { documentSnapshot ->
                    messages.add(SentText(documentSnapshot["message"].toString(), documentSnapshot["senderId"].toString()))
                }
                Column {
                    LazyColumn(
                        verticalArrangement = Arrangement.Bottom,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(550.dp)
                    ) {

                        items(messages) { singleText ->
                            if (singleText.senderUid != userId) {
                                CurrentUserChatBubble(currentUserText = singleText.text)
                            } else {
                                OtherUserChatBubble(currentUserText = singleText.text)
                            }
                        }
                    }
                    // input row
                    Row(modifier = Modifier
                        .fillMaxSize()
                        .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(2.dp)), horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
                        InputTextField(
                            inputValue = textToSend,
                            inputValueOnChange = { textToSend = it },
                            label = "Text",
                            isVisible = true,
                            isError = false,
                            focusManager = focusManager,
                            focusRequester = focusRequester
                        )
                        Button(onClick = {
                            val senderRef =
                                currentUserId?.let { currentId ->
                                    firestore.collection("users").document(currentId).collection("chats").document(collectRef).collection("messages")
                                }
                            val receiverRef = firestore.collection("users").document(userId).collection("chats").document(collectRef).collection("messages")
                            Log.d("hello","sender ref ${senderRef!!.path}")
                            Log.d("hello","receiver ref ${receiverRef.path}")
                            viewModel.sendText(textToSend, receiverRef, senderRef!!)
                            textToSend = ""


                        }) {
                            Text(text = "SEND")
                        }
                    }
                }
            }
        }

    }

}