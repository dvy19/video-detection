package com.example.assignment_well


import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri

fun getVideoThumbnail(
    context: Context,
    uri: Uri
): Bitmap? {

    val retriever = MediaMetadataRetriever()

    return try {

        retriever.setDataSource(
            context,
            uri
        )

        retriever.getFrameAtTime(
            1_000_000,
            MediaMetadataRetriever.OPTION_CLOSEST_SYNC
        )

    } catch (e: Exception) {

        e.printStackTrace()
        null

    } finally {

        retriever.release()
    }
}