package com.caique.aetnatestflickr

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.rememberNavController
import com.caique.aetnatestflickr.feature.list.presentation.PhotoList
import com.caique.aetnatestflickr.ui.design.AppTheme
import org.junit.Rule
import org.junit.Test

class SearchingTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun searching() {
        with(composeTestRule) {
            setScreenContent()

            performSearch("car")

            //After the list is shown, list has items?
            onAllNodesWithTag("item")
                .fetchSemanticsNodes().size == 1
        }
    }

    @Test
    fun searchingEmpty() {
        with(composeTestRule) {
            setScreenContent()

            performSearch("slcrhncsifsadboucfgbosuadvtgbouawboiscybtoisvdtiousdy")

            //After the list is shown, list has items?
            onAllNodesWithTag("item")
                .fetchSemanticsNodes().isEmpty()
        }
    }

    @Test
    fun screenWithRecentSearchesShouldNotShowEmptyState() {
        with(composeTestRule){
            setScreenContent()

            performSearch("car")

            performSearch("")

            onAllNodesWithTag("empty")
                .fetchSemanticsNodes().isEmpty()
        }
    }

    private fun ComposeContentTestRule.performSearch(text: String) {
        onNodeWithTag("searchTextField")
            .performTextInput(text)

        onNodeWithTag("searchTextField")
            .performImeAction()

        //After hitting search, is loading visible?
        onNodeWithTag("loading").assertExists()

        waitUntil(10000L) {
            onAllNodesWithTag("loading")
                .fetchSemanticsNodes().isEmpty()
        }
    }

    private fun ComposeContentTestRule.setScreenContent() {
        setContent {
            AppTheme {
                PhotoList(navController = rememberNavController())
            }
        }
    }
}