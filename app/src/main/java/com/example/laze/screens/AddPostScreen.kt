package com.example.laze.screens


import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.laze.composables.CameraPreview

@Composable
fun AddPostScreen(navController: NavController) {
    CameraPreview(navController = navController)
}

