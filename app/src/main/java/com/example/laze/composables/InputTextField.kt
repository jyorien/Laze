package com.example.laze.composables

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.laze.R

@Composable
fun InputTextField(
    inputValue: String,
    inputValueOnChange: (String) -> Unit,
    label: String,
    isVisible: Boolean,
    isError: Boolean,
    focusManager: FocusManager,
    focusRequester: FocusRequester,
    maxLines: Int = 1,
    singleLine: Boolean = true

) {
    Box {
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            isError = isError,
            value = inputValue,
            onValueChange = { inputValueOnChange(it) },
            modifier = Modifier
                .padding(top = 10.dp)
                .focusRequester(focusRequester),
            visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                if (isError) Icon(
                    painterResource(id = R.drawable.ic_baseline_error_24),
                    "Error icon"
                )
            },
            singleLine = singleLine,
            maxLines = maxLines,

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