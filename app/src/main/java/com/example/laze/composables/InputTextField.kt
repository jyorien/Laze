package com.example.laze.composables

import android.widget.RelativeLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun InputTextField(inputValue: String, inputValueOnChange: (String) -> Unit, label: String) {
    Box {
        OutlinedTextField(value = inputValue, onValueChange = { inputValueOnChange(it) },modifier = Modifier.padding(top = 10.dp))
        Box(modifier = Modifier.padding(bottom = 10.dp, start = 10.dp)) {
            Text(label,
                Modifier
                    .wrapContentWidth(Alignment.Start)
                    .background(Color.White))

        }
    }

}

@Preview(showBackground = false)
@Composable
fun PreviewInputTextField() {
    InputTextField(inputValue = "", inputValueOnChange = {}, label = "Email")
}