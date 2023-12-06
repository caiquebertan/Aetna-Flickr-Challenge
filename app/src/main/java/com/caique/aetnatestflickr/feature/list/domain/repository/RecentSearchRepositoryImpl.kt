package com.caique.aetnatestflickr.feature.list.domain.repository

import android.content.SharedPreferences
import com.caique.aetnatestflickr.data.domain.RecentSearchRepository

class RecentSearchRepositoryImpl(
    private val preferences: SharedPreferences
): RecentSearchRepository {

    override suspend fun getRecentSearchQueries(): List<String> {
        return (preferences
            .getString("searches", null)
            ?.split(";")
                ?: emptyList()).asReversed()
    }

    override suspend fun updateRecentSearchs(searches: List<String>) {
        preferences
            .edit()
            .putString("searches", searches.joinToString(";"))
            .apply()
    }
}

