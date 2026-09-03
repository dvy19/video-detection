package com.example.assignment_well

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class VideoViewModel : ViewModel() {

    private val _selectedVideo = MutableStateFlow<Uri?>(null)

    val selectedVideo: StateFlow<Uri?> = _selectedVideo

    fun setSelectedVideo(uri: Uri) {
        _selectedVideo.value = uri
    }

    fun clearVideo() {
        _selectedVideo.value = null
    }
}