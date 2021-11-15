package com.example.laze.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SquareBox(content: @Composable () -> Unit) {
    Card(modifier = Modifier
        .border(BorderStroke(1.dp, Color.Black))
        .width(560.dp)
        .padding(horizontal = 20.dp, vertical = 60.dp)) {
        content()
    }


}

@Preview
@Composable
fun PreviewSquareBox() {
    SquareBox {
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            TextField(value = "Hello", onValueChange = {

            })

            TextField(value = "Hello", onValueChange = {

            })
        }

    }
}