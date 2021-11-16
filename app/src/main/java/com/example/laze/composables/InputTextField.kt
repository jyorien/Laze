package com.example.laze.composables

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.laze.R

@Composable
fun InputTextField(
    inputValue: String,
    inputValueOnChange: (String) -> Unit,
    label: String,
    isVisible: Boolean,
    isError: Boolean,
) {
    Box {
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            isError = isError,
            value = inputValue,
            onValueChange = { inputValueOnChange(it) },
            modifier = Modifier.padding(top = 10.dp),
            visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                if (isError) Icon(
                    painterResource(id = R.drawable.ic_baseline_error_24),
                    "Error icon"
                )
            }
        )
        Box(modifier = Modifier.padding(bottom = 10.dp, start = 10.dp)) {
            Text(
                label,
                Modifier
                    .wrapContentWidth(Alignment.Start)
                    .background(Color.White)
            )

        }
    }

}

@Preview(showBackground = false)
@Composable
fun PreviewInputTextField() {
    InputTextField(
        inputValue = "",
        inputValueOnChange = {},
        label = "Email",
        isVisible = true,
        false
    )
}