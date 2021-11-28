package com.example.laze.tabs

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun TakenUpTab() {
    Log.d("hello","Taken Up Tab")
    Column(modifier = Modifier.fillMaxSize().background(Color.Gray), verticalArrangement = Arrangement.Center) {
        Text(text = "Taken Up Tab",fontSize = 56.sp)

    }

}