package com.caique.aetnatestflickr.feature.list.domain.interactor

import com.caique.aetnatestflickr.data.domain.RecentSearchRepository

class AddRecentSearchUseCase(
    private val repository: RecentSearchRepository,
) {
    suspend operator fun invoke(
        searchQuery: String,
    ): List<String> {
        if(searchQuery.isNotBlank()) {
            val searches = repository.getRecentSearchQueries().toMutableList()

            if(searches.contains(searchQuery)){
                searches.remove(searchQuery)
            } else if (searches.size >= 5)
                searches.removeLast()

            searches.add(0, searchQuery)

            repository.updateRecentSearchs(searches.reversed())

            return searches
        }
        return emptyList()
    }
}