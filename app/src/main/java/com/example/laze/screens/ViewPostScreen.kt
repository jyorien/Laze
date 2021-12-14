package com.example.laze.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.laze.R
import com.example.laze.composables.Pager
import com.example.laze.data.MainViewModel
import com.example.laze.data.OnError
import com.example.laze.data.OnSuccess
import com.example.laze.data.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ViewPostScreen(viewModel: MainViewModel, navController: NavController) {
    val storage = FirebaseStorage.getInstance()
    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()
    when (val posts = viewModel.postsStateFlow.asStateFlow().collectAsState().value) {
        is OnError -> {
            Text(text = "Posts couldn't load")
        }

        is OnSuccess -> {
            val postsList = mutableListOf<Post>()
            runBlocking {
                val data = posts.querySnapshot?.documents?.forEach { document ->
                    val data = document.data
                    data?.let {
                        val url = storage.getReference("/${data["imageUrl"]}").downloadUrl.await()
                        val userId = data["imageUrl"].toString().split("?")[0]
                        val post = Post(data["name"].toString(), data["description"].toString(),url.toString(), userId, data["imageUrl"].toString())
                        postsList.add(post)
                    }


                }
            }


                Pager(
        items = postsList,
        modifier = Modifier.fillMaxSize(), orientation = Orientation.Vertical,
        contentFactory = { item ->
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(painter = rememberImagePainter(data = item.imageUrl, builder = { placeholder(R.drawable.sample_placeholder) }), contentDescription = "Image", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .padding(20.dp)
                    .height(190.dp)) {
                    Column(Modifier.fillMaxWidth()) {
                        Row(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column() {
                                Text(text = item.username, color = Color.White, fontSize = 36.sp, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(20.dp))
                                Text(text = item.description, color = Color.White)
                            }

                            Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxHeight()) {
                                Icon(painter = painterResource(id = R.drawable.ic_baseline_chat_bubble_24), contentDescription = "Chat with User button", modifier = Modifier.clickable {
                                    val userId = item.userId
                                    val collection =
                                        auth.currentUser?.uid?.let { uid ->
                                            firestore.collection("users").document(uid).collection("chats").document("${item.rawImageRef}?$uid").collection("messages")
                                        }
                                    if (collection != null) {
                                        viewModel.subscribeToMessageThread(collection)
                                    }
                                    navController.navigate("PrivateChatScreen/$userId")
                                })
                            }
                        }
                    }

                }
            }
        }
    )
        }

    }

}