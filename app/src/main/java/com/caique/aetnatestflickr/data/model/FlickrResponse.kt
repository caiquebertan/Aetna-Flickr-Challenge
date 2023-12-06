package com.caique.aetnatestflickr.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class FlickrResponse(
    val items: List<PhotoItem>
)

@Parcelize
class PhotoItem(
    val title: String,
    val media: Media,
    val description: String,
    val author: String,
    val tags: String
): Parcelable {
    fun getTags() = tags.split(" ")
    fun getWidthAndHeight(): Pair<Int, Int>? {
        val regex = """width=\"(\d+)\" height=\"(\d+)\"""".toRegex()
        val matchResult = regex.find(description)

        return matchResult?.let {
            val width = it.groupValues[1].toIntOrNull()
            val height = it.groupValues[2].toIntOrNull()

            if (width != null && height != null) {
                Pair(width, height)
            } else {
                null
            }
        }
    }
}

@Parcelize
data class Media(
    val m: String,
): Parcelable
