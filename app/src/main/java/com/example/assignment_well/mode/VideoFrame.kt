package com.example.assignment_well.mode

// This represents one frame extracted from the video.
/*
VideoFrame(
    bitmap = frame image,
    timestampMs = 2500
)

This image came from 2.5 seconds into the video.
 */

import android.graphics.Bitmap

data class VideoFrame(
    val bitmap: Bitmap,
    val timestampMs: Long
)