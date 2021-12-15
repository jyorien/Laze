package com.example.laze.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.laze.data.ChatRoom
import com.example.laze.data.MainViewModel
import com.example.laze.data.OnError
import com.example.laze.data.OnSuccess
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.asStateFlow

@Composable
fun ChatListScreen(viewModel: MainViewModel, navController: NavController) {
    viewModel.getAllChats()
    val firestore = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    Scaffold {
        when(val response = viewModel.chatsStateFlow.asStateFlow().value) {
            is OnError -> {
                Text("Oops! Something went wrong.")
            }

            is OnSuccess -> {
                val chatRoomList = mutableListOf<ChatRoom>()
                response.querySnapshot?.documents?.forEach { documentSnapshot ->
                    val chatRoom = ChatRoom(chatRoomId = documentSnapshot.id, senderId = documentSnapshot["senderId"].toString(), senderUsername = documentSnapshot["senderUsername"].toString())
                    chatRoomList.add(chatRoom)
                }
                Log.d("hello","chat room list $chatRoomList")
                Column(modifier = Modifier.fillMaxSize()) {
                    LazyColumn(Modifier.fillMaxSize()) {
                        items(chatRoomList) { item ->
                            Box(
                                Modifier
                                    .padding(vertical = 5.dp, horizontal = 2.dp)
                                    .clickable {
                                        auth.currentUser?.uid.let { uid ->
                                            val collectionRef = firestore
                                                .collection("users")
                                                .document(uid.toString())
                                                .collection("chats")
                                                .document(item.chatRoomId)
                                                .collection("messages")
                                            viewModel.subscribeToMessageThread(collectionRef)

                                            val arr = item.chatRoomId.split("?")
                                            navController.navigate("ListPrivateChatScreen/${item.senderId}/${arr[0]}/${arr[1]}/${arr[2]}")
                                        }


                                    }) {
                                Row(modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .border(1.dp, Color.Black)
                                    .padding(10.dp)
                                ) {
                                    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
                                        Text(item.senderUsername, fontWeight = FontWeight.Bold, fontSize = 24.sp)
                                        Text("Chat Room Id: ${item.chatRoomId}")
                                    }
                                }
                            }


                        }
                    }
                }
            }

        }
    }
}