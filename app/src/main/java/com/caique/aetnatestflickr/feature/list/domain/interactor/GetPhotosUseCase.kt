package com.caique.aetnatestflickr.feature.list.domain.interactor

import com.caique.aetnatestflickr.data.domain.FlickrRepository

class GetPhotosUseCase(
    private val repository: FlickrRepository,
) {

    suspend operator fun invoke(
        searchQuery: String,
    ) = repository.searchPhotos(searchQuery)
}