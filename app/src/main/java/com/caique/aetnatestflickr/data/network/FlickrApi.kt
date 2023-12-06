package com.caique.aetnatestflickr.data.network

import com.caique.aetnatestflickr.data.model.FlickrResponse
import com.caique.aetnatestflickr.data.model.PhotoItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {
    @GET("services/feeds/photos_public.gne?format=json&nojsoncallback=1")
    suspend fun searchPhotos(@Query("tags") query: String): Response<FlickrResponse>
}
