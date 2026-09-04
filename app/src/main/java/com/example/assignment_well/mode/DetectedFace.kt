package com.example.assignment_well.mode


import android.graphics.Rect

data class DetectedFace(
    val boundingBox: Rect,
    val trackingId: Int?,
    val headEulerAngleY: Float,
    val headEulerAngleZ: Float,
    val leftEyeOpenProbability: Float?,
    val rightEyeOpenProbability: Float?,
    val smilingProbability: Float?
)