package com.example.laze.composables

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.laze.R
import com.google.gson.Gson
import java.lang.Exception
import java.nio.ByteBuffer

@Composable
fun CameraPreview(
    navController: NavController
) {
    // ui states
    var isCameraAllowed by remember { mutableStateOf(false) }

    // context
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    // camera configurations
    val imageCapture = ImageCapture.Builder().build()
    val cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    // preview view
    val previewView = remember {
        PreviewView(context).apply {
            id = R.id.preview_view
            implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            scaleType = PreviewView.ScaleType.FILL_CENTER
        }
    }
    // launch permission request
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted ->
            isCameraAllowed = isGranted
            Log.d("hello", "allowed: $isCameraAllowed")
        }

    isCameraAllowed =
        context.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED

    // request permission if not allowed
    if (!isCameraAllowed) {
        // https://developer.android.com/jetpack/compose/side-effects
        SideEffect {
            launcher.launch(Manifest.permission.CAMERA)
        }
    } else {
        Box(Modifier.fillMaxSize()) {
            // Camera View
            AndroidView(modifier = Modifier.fillMaxSize(), factory = { previewView }) {
                cameraProviderFuture.addListener({
                    val cameraProvider = cameraProviderFuture.get()

                    // Provides a stream for camera to display onto our Preview View
                    val preview = Preview.Builder()
                        .build()
                        .also { it.setSurfaceProvider(previewView.surfaceProvider) }

                    try {
                        // Must unbind the use-cases before rebinding them.
                        cameraProvider.unbindAll()

                        cameraProvider.bindToLifecycle(
                            lifecycleOwner, cameraSelector, preview, imageCapture
                        )
                    } catch (exc: Exception) {
                        Log.d("hello", "Use case binding failed", exc)
                    }

                }, ContextCompat.getMainExecutor(context))
            }

            // Button
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(vertical = 80.dp)
            ) {
                FloatingActionButton(onClick = {
                    // take a picture
                    imageCapture.takePicture(ContextCompat.getMainExecutor(context), object : ImageCapture.OnImageCapturedCallback() {
                        override fun onCaptureSuccess(image: ImageProxy) {
                            super.onCaptureSuccess(image)
                            val img = Gson().toJson(imageProxyToBitmap(image))
                            Log.d("hello", "image captured")
                            navController.navigate("CapturedImageScreen/$img")
                        }

                        override fun onError(exception: ImageCaptureException) {
                            super.onError(exception)
                            Log.d("hello", exception.localizedMessage!!)
                        }
                    })
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_camera_alt_24),
                        contentDescription = "Take a picture button"
                    )
                }
            }
        }
    }
}

private fun imageProxyToBitmap(image: ImageProxy): Bitmap {
    val planeProxy = image.planes[0]
    val buffer: ByteBuffer = planeProxy.buffer
    val bytes = ByteArray(buffer.remaining())
    buffer.get(bytes)
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
}