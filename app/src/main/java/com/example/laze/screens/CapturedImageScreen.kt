package com.example.laze.screens

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.asImageBitmap

@Composable
fun CapturedImageScreen(img: Bitmap) {
    Scaffold {
        Image(bitmap = img.asImageBitmap(), contentDescription = "Captured image")
    }

}