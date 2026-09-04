package com.example.assignment_well

import com.example.assignment_well.mode.FrameFaceResult
import com.example.assignment_well.mode.VideoFrame

class FaceDetectionProcessor(
    private val faceDetector: FaceDetector
) {

    suspend fun process(
        frames: List<VideoFrame>,
        onProgress: (Int) -> Unit
    ): List<FrameFaceResult> {

        val results = mutableListOf<FrameFaceResult>()

        frames.forEachIndexed { index, frame ->

            val faces = faceDetector.detect(frame.bitmap)

            results.add(
                FrameFaceResult(
                    timestampMs = frame.timestampMs,
                    bitmap = frame.bitmap,
                    faces = faces
                )
            )

            val progress =
                ((index + 1) * 100) / frames.size

            onProgress(progress)
        }

        return results
    }
}