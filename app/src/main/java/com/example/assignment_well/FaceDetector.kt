package com.example.assignment_well


import android.graphics.Bitmap
import com.example.assignment_well.mode.DetectedFace
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FaceDetector {

    private val options = FaceDetectorOptions.Builder()
        .setPerformanceMode(
            FaceDetectorOptions.PERFORMANCE_MODE_FAST
        )
        .setLandmarkMode(
            FaceDetectorOptions.LANDMARK_MODE_NONE
        )
        .setClassificationMode(
            FaceDetectorOptions.CLASSIFICATION_MODE_ALL
        )
        .setMinFaceSize(0.10f)
        .build()

    private val detector = FaceDetection.getClient(options)

    suspend fun detect(bitmap: Bitmap): List<DetectedFace> {

        val image = InputImage.fromBitmap(bitmap, 0)

        return suspendCancellableCoroutine { continuation ->

            detector.process(image)
                .addOnSuccessListener { faces ->

                    val result = faces.map { face ->

                        DetectedFace(
                            boundingBox = face.boundingBox,
                            trackingId = face.trackingId,
                            headEulerAngleY = face.headEulerAngleY,
                            headEulerAngleZ = face.headEulerAngleZ,
                            leftEyeOpenProbability =
                            face.leftEyeOpenProbability,
                            rightEyeOpenProbability =
                            face.rightEyeOpenProbability,
                            smilingProbability =
                            face.smilingProbability
                        )
                    }

                    continuation.resume(result)
                }
                .addOnFailureListener { error ->
                    continuation.resumeWithException(error)
                }
        }
    }

    fun close() {
        detector.close()
    }
}