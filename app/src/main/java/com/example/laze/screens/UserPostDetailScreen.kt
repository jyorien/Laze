package com.example.laze.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.laze.R
import com.example.laze.data.Post
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

@ExperimentalCoilApi
@Composable
fun UserPostDetailScreen(data: String, navController: NavController) {
    val storage = FirebaseStorage.getInstance()
    val dataList = data.split("$")
    var post: Post
    val imgUrl = dataList[2] + "?" + dataList[3]
    runBlocking {
        Log.d("hello","img $imgUrl")
        val url = storage.getReference("/${imgUrl}").downloadUrl.await()
        post = Post(dataList[0], dataList[1], url.toString(),"")
    }

    Scaffold() {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(painter = rememberImagePainter(data = post.imageUrl, builder = { placeholder(R.drawable.sample_placeholder) }), contentDescription = "Image", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
            Box(modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .padding(20.dp)
                .height(190.dp) ) {
                Column {
                    Text(text = post.username, color = Color.White, fontSize = 36.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(text = post.description, color = Color.White)
                }

            }
        }
    }
}