package com.example.assignment_well.mode

data class FrameFaceResult(
    val timestampMs: Long,
    val bitmap: android.graphics.Bitmap,
    val faces: List<DetectedFace>
)