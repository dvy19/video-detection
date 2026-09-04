package com.example.assignment_well.video


import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.media.MediaMetadataRetriever
import com.example.assignment_well.mode.VideoFrame

class VideoFrameExtractor(
    private val context: Context
) {

    fun extractFrames(
        videoUri: Uri,
        intervalMs: Long = 500,
        onProgress: (Int) -> Unit
    ): List<VideoFrame> {

        val frames = mutableListOf<VideoFrame>()

        val retriever = MediaMetadataRetriever()

        try {
            retriever.setDataSource(context, videoUri)

            val durationMs =
                retriever.extractMetadata(
                    MediaMetadataRetriever.METADATA_KEY_DURATION
                )?.toLongOrNull() ?: 0L

            var timestampMs = 0L

            while (timestampMs < durationMs) {

                val bitmap = retriever.getFrameAtTime(
                    timestampMs * 1000,
                    MediaMetadataRetriever.OPTION_CLOSEST
                )

                if (bitmap != null) {
                    frames.add(
                        VideoFrame(
                            bitmap,
                            timestampMs
                        )
                    )
                }

                val progress =
                    ((timestampMs.toFloat() / durationMs) * 100)
                        .toInt()

                onProgress(progress.coerceIn(0, 100))

                timestampMs += intervalMs
            }
            onProgress(100)

        } finally {
            retriever.release()
        }

        return frames
    }
}