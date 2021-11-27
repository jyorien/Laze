package com.example.laze.screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.laze.R
import com.example.laze.composables.InputTextField
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun CapturedImageScreen(imgUri: String, navController: NavController) {
    // states
    var descText by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    // firebase services
    val firestore = FirebaseFirestore.getInstance()
    val firestorage = FirebaseStorage.getInstance().reference
    val auth = FirebaseAuth.getInstance()

    // get uri to image
    val decodedUri = imgUri.replace("*", "/").replace("file:///", "")
    Log.d("hello", "decoded uri $decodedUri")
    val imgFile = File(decodedUri)

    val bitmap = BitmapFactory.decodeFile(imgFile.absolutePath)

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = {
            val storagePath = auth.currentUser!!.uid + "?" + System.currentTimeMillis()
            // save to firebase storage
            firestorage.child(storagePath).putFile(Uri.fromFile(imgFile))
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val data = mapOf("description" to descText, "imageUrl" to storagePath, "name" to auth.currentUser!!.displayName)

                        // save to firestore
                        firestore.collection("users").document(auth.currentUser!!.uid)
                            .collection("uploads").add(data).addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Log.d("hello", "Upload success: ${task.result}")
                                    coroutineScope.launch {
                                        val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(message = "Successfully posted", duration = SnackbarDuration.Short)
                                        if (snackbarResult == SnackbarResult.Dismissed) navigateBackHome(navController)
                                    }

                                } else {
                                    Log.e("hello", "Error uploading to firestore: ${task.exception!!.message}" )
                                }
                            }
                    } else {
                        Log.e("hello", "Error uploading file to storage: ${task.exception}")
                    }
                }
        }, Modifier.padding(bottom = 60.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_arrow_forward_24),
                contentDescription = "Enter button"
            )
        }
    }, scaffoldState = scaffoldState) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            InputTextField(
                inputValue = descText,
                inputValueOnChange = { descText = it },
                label = "Description",
                isVisible = true,
                isError = false,
                focusManager = focusManager,
                focusRequester = focusRequester,
                maxLines = 3,
                singleLine = false,
                height = 150.dp
            )
            Spacer(Modifier.height(20.dp))
            Image(bitmap = bitmap.asImageBitmap(), contentDescription = "")
        }
    }
}

private fun navigateBackHome(navController: NavController) {
    navController.navigate("ViewPostScreen")
}