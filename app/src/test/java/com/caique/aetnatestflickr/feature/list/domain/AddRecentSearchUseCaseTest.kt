package com.caique.aetnatestflickr.feature.list.domain

import com.caique.aetnatestflickr.data.domain.RecentSearchRepository
import com.caique.aetnatestflickr.feature.list.domain.interactor.AddRecentSearchUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class AddRecentSearchUseCaseTest {

    @Mock
    private lateinit var repository: RecentSearchRepository
    @InjectMocks
    private lateinit var addRecentSearchUseCase: AddRecentSearchUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        addRecentSearchUseCase = AddRecentSearchUseCase(repository)
    }

    @Test
    fun `when searchQuery is blank, return empty list`() = runBlocking {
        val result = addRecentSearchUseCase("")
        assertEquals(emptyList<String>(), result)
    }
    
    @Test
    fun `when searchQuery is not in recent searches, add it to the top`() = runBlocking {
        `when`(repository.getRecentSearchQueries())
            .thenReturn(listOf("query1", "query2", "query3"))

        val result = addRecentSearchUseCase("newQuery")

        assertEquals(listOf("newQuery", "query1", "query2", "query3"), result)
    }

    @Test
    fun `when searchQuery is already in recent searches, move it to the top`() = runBlocking {
        `when`(repository.getRecentSearchQueries())
            .thenReturn(listOf("query1", "query2", "query3"))
        val searchQuery = "query2"
        val result = addRecentSearchUseCase(searchQuery)

        assertEquals(listOf("query2", "query1", "query3"), result)
    }

    @Test
    fun `when recent searches have 5 items, remove the last one`() = runBlocking {
        `when`(repository.getRecentSearchQueries())
            .thenReturn(listOf("query1", "query2", "query3", "query4", "query5"))

        val result = addRecentSearchUseCase("newQuery")

        assertEquals(listOf("newQuery", "query1", "query2", "query3", "query4"), result)
    }

    @Test
    fun `when searchQuery is already in recent searches, and list has 5 items move it the query that is already on the list to the top`() = runBlocking {
        `when`(repository.getRecentSearchQueries())
            .thenReturn(listOf("query1", "query2", "query3", "query4", "query5"))
        val searchQuery = "query2"
        val result = addRecentSearchUseCase(searchQuery)

        assertEquals(listOf("query2", "query1", "query3", "query4", "query5"), result)
    }

    @Test
    fun `when searchQuery is not on the list and list has 5 items move it the query to the top and remove the last one`() = runBlocking {
        `when`(repository.getRecentSearchQueries())
            .thenReturn(listOf("query1", "query2", "query3", "query4", "query5"))
        val searchQuery = "query6"
        val result = addRecentSearchUseCase(searchQuery)

        assertEquals(listOf("query6", "query1", "query2", "query3", "query4"), result)
    }

}
