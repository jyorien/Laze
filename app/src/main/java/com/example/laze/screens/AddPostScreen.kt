package com.example.laze.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.laze.R
import com.example.laze.composables.CameraPreview

@Composable
fun AddPostScreen() {

    Box(Modifier.fillMaxSize()) {
        CameraPreview()
        Box(modifier = Modifier.align(Alignment.BottomCenter).padding(vertical = 80.dp)) {
            Button(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_camera_alt_24),
                    contentDescription = "Take a picture button"
                )
            }
        }

    }


}