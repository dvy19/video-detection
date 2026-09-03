package com.example.videodetection.ui.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.asImageBitmap
import com.example.assignment_well.getVideoThumbnail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun VideoPreview(
    videoUri: Uri
) {

    val context = LocalContext.current

    var thumbnail by remember(videoUri) {
        mutableStateOf<android.graphics.Bitmap?>(null)
    }

    LaunchedEffect(videoUri) {

        thumbnail = withContext(Dispatchers.IO) {

            getVideoThumbnail(
                context,
                videoUri
            )
        }
    }

    thumbnail?.let { bitmap ->

        Image(
            bitmap = bitmap.asImageBitmap(),
            contentDescription = "Selected video",
            modifier = Modifier
                .width(250.dp)
                .height(350.dp)
        )
    }
}