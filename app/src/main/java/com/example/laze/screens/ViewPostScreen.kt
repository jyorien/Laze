package com.example.laze.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.laze.composables.Pager

@Composable
fun ViewPostScreen() {
    val items = listOf(
        Color.Red,
        Color.Blue,
        Color.Green,
        Color.Yellow,
        Color.Cyan,
        Color.Magenta
    )
    Pager(
        items = items,
        modifier = Modifier.fillMaxSize(), orientation = Orientation.Vertical,
        itemSpacing = 16.dp,
        contentFactory = { item ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(item),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item.toString(),
                    modifier = Modifier.padding(all = 16.dp),
                    style = MaterialTheme.typography.h6,
                )
            }
        }
    )
}