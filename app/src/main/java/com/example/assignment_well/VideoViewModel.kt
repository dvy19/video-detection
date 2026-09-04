package com.example.assignment_well

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment_well.mode.FrameFaceResult
import com.example.assignment_well.mode.VideoFrame
import com.example.assignment_well.video.VideoFrameExtractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VideoViewModel(
    application: Application
) : AndroidViewModel(application) {


    private val _selectedVideo = MutableStateFlow<Uri?>(null)

    val selectedVideo: StateFlow<Uri?> = _selectedVideo

    private val frameExtractor =
        VideoFrameExtractor(application)

    val detector = FaceDetector()

    private val faceDetectionProcessor =
        FaceDetectionProcessor(detector)

    private val _faceResults =
        MutableStateFlow<List<FrameFaceResult>>(emptyList())

    val faceResults: StateFlow<List<FrameFaceResult>> =
        _faceResults.asStateFlow()

    private val _faceDetectionProgress =
        MutableStateFlow(0)

    val faceDetectionProgress: StateFlow<Int> =
        _faceDetectionProgress.asStateFlow()



    fun extractFrames(
        videoUri: Uri,
        onProgress: (Int) -> Unit,
        onResult: (List<VideoFrame>) -> Unit
    ) {

        viewModelScope.launch {

            val frames = withContext(Dispatchers.IO) {

                frameExtractor.extractFrames(
                    videoUri = videoUri,
                    intervalMs = 500,
                    onProgress = onProgress
                )
            }



            onResult(frames)
            detectFaces(frames)
        }
    }

    fun detectFaces(frames: List<VideoFrame>) {

        viewModelScope.launch(Dispatchers.Default) {

            try {
                val results = faceDetectionProcessor.process(
                    frames = frames,
                    onProgress = { progress ->
                        _faceDetectionProgress.value = progress
                    }
                )

                _faceResults.value = results

                Log.d(
                    "FaceDetection",
                    "Completed: ${results.size} frames"
                )

            } catch (e: Exception) {
                Log.e(
                    "FaceDetection",
                    "Face detection failed",
                    e
                )
            }
        }
    }

    fun setSelectedVideo(uri: Uri) {
        _selectedVideo.value = uri
    }

    fun clearVideo() {
        _selectedVideo.value = null
    }
}