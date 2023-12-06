package com.caique.aetnatestflickr.feature.list.presentation

import com.caique.aetnatestflickr.data.model.Media
import com.caique.aetnatestflickr.data.model.PhotoItem
import com.caique.aetnatestflickr.feature.list.domain.interactor.AddRecentSearchUseCase
import com.caique.aetnatestflickr.feature.list.domain.interactor.GetPhotosUseCase
import com.caique.aetnatestflickr.feature.list.domain.interactor.GetRecentSearchesUseCase
import com.caique.aetnatestflickr.util.MainDispatcherRule
import com.caique.aetnatestflickr.util.ResultState
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ListViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val getPhotosUseCase = mockk<GetPhotosUseCase>(relaxed = true)
    private val addRecentSearchUseCase = mockk<AddRecentSearchUseCase>(relaxed = true)
    private val getRecentSearchesUseCase = mockk<GetRecentSearchesUseCase>(relaxed = true)

    private lateinit var listViewModel: ListViewModel

    @Before
    fun setup() {
        listViewModel = ListViewModel(
            getPhotosUseCase = getPhotosUseCase,
            getRecentSearchesUseCase = getRecentSearchesUseCase,
            addRecentSearchUseCase = addRecentSearchUseCase)
    }

    @Test
    fun `when search is successful, update photos and loading state with empty list`() = runTest {
        coEvery { getPhotosUseCase("teste") } returns ResultState.Success(emptyList())

        listViewModel.search("teste")

        listViewModel.uiListState.first().let {
            assertEquals(it.searchText, "teste")
            assertEquals(it.photos.size, 0)
        }

    }

    @Test
    fun `when search is successful, update photos and loading state with list`() = runTest {
        val photoItems = mutableListOf<PhotoItem>()
        (1..5).forEach {
            photoItems.add(PhotoItem(
                title = "Photo $it",
                media = Media(m = "", width = 100, height = 100),
                description = "Lorep ipsum $it",
                author = "Caique Bertan",
                tags = "teste $it"
            ))
        }

        coEvery { getPhotosUseCase("teste") } returns ResultState.Success(photoItems)

        listViewModel.search("teste")

        listViewModel.uiListState.first().let {
            assertEquals(it.searchText, "teste")
            assertEquals(it.photos.size, 5)
        }

    }

    @Test
    fun `when search is error, result state should be error`() = runTest {
        val photoItems = mutableListOf<PhotoItem>()
        (1..5).forEach {
            photoItems.add(PhotoItem(
                title = "Photo $it",
                media = Media(m = "", width = 100, height = 100),
                description = "Lorep ipsum $it",
                author = "Caique Bertan",
                tags = "teste $it"
            ))
        }

        coEvery { getPhotosUseCase("teste") } returns ResultState.Error

        listViewModel.search("teste")

        listViewModel.uiListState.first().let {
            assertEquals(it.searchText, "")
            assertEquals(it.photos.size, 0)
        }

    }
}