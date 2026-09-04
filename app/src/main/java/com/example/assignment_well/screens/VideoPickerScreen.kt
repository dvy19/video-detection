package com.example.assignment_well.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.assignment_well.VideoViewModel
import com.example.videodetection.ui.components.VideoPreview

@Composable
fun VideoPickerScreen(
    innerPadding: PaddingValues,
    viewModel: VideoViewModel = viewModel()
) {

    var isProcessing by remember {
        mutableStateOf(false)
    }

    var frameCount by remember {
        mutableStateOf(0)
    }

    val faceDetectionProgress by viewModel.faceDetectionProgress.collectAsState()

    val selectedVideo by viewModel.selectedVideo.collectAsState()

    val selectedVideoUri by viewModel.selectedVideo.collectAsState()

    var progress by remember {
        mutableStateOf(0)
    }

    val videoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->

        uri?.let {
            viewModel.setSelectedVideo(it)
        }
    }

    val faceResults by viewModel.faceResults.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Button(
            onClick = {
                videoPicker.launch("video/*")
            }
        ) {
            Text("Select Video")
        }

        if (selectedVideo != null) {

            VideoPreview(
                videoUri = selectedVideo!!
            )

        }


        LazyColumn {
            items(faceResults) { result ->
                Text(
                    text = "Frame @ ${result.timestampMs}ms → ${result.faces.size} faces"
                )
            }
        }


        Button(
            onClick = {

                selectedVideoUri?.let { uri ->

                    viewModel.extractFrames(
                        videoUri = uri,
                        onProgress = {
                            progress = it
                        },
                        onResult = { frames ->
                            frameCount=frames.size

                            println("Finished: ${frames.size} frames")
                        }
                    )
                }
            }
        ) {
            Text("Process Video")
        }


        if (progress > 0 && progress < 100) {

            Text("Extracting frames: $progress%")

            LinearProgressIndicator(
                progress = { progress / 100f }
            )
        }
        else if (progress == 100) {

            Text("Frame extraction completed!")

            Text("Frames extracted: $frameCount")
        }


        if (faceDetectionProgress > 0 && faceDetectionProgress < 100) {
            Text(
                text = "Detecting faces... $faceDetectionProgress%"
            )

            LinearProgressIndicator(
                progress = { faceDetectionProgress / 100f },
                modifier = Modifier.fillMaxWidth()
            )
        }
        if (faceDetectionProgress == 100) {
            Text("Face detection completed")
        }

    }
}