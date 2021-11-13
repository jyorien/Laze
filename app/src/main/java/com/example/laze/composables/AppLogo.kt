package com.example.laze.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.laze.R

@Composable
fun AppLogo() {
    Box(modifier = Modifier.width(150.dp)) {
        Image(painter = painterResource(id = R.drawable.appicon), contentDescription = "App Icon")
        Text(text = "Laze", style = MaterialTheme.typography.h1, fontSize = 30.sp, modifier = Modifier.fillMaxWidth().align(Alignment.BottomEnd), textAlign = TextAlign.Center)


    }

}