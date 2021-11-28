package com.example.laze.tabs

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun PostedTab() {
    Log.d("hello","Posted Tab")
    Column(modifier = Modifier.fillMaxSize().background(Color.Cyan), verticalArrangement = Arrangement.Center) {
        Text(text = "Posted Tab",fontSize = 56.sp, color = Color.Black)

    }
}