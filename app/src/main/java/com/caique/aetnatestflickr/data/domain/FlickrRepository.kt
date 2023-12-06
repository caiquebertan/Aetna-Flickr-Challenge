package com.caique.aetnatestflickr.data.domain

import com.caique.aetnatestflickr.core.safeApiCall
import com.caique.aetnatestflickr.data.model.PhotoItem
import com.caique.aetnatestflickr.data.network.FlickrApi
import com.caique.aetnatestflickr.util.ResultState

class FlickrRepository(private val flickrApi: FlickrApi) {
    suspend fun searchPhotos(query: String): ResultState<List<PhotoItem>> = safeApiCall(
        remote = { flickrApi.searchPhotos(query) },
        remoteMapper = {
            it?.items ?: emptyList()
        }
    )
}
