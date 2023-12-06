package com.caique.aetnatestflickr.feature.list.domain.interactor

import com.caique.aetnatestflickr.data.domain.RecentSearchRepository

class GetRecentSearchesUseCase (
    private val recentSearchRepository: RecentSearchRepository,
) {
    suspend operator fun invoke(): List<String> =
        recentSearchRepository.getRecentSearchQueries()
}
