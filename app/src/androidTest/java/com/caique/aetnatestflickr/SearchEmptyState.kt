package com.caique.aetnatestflickr

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.rememberNavController
import com.caique.aetnatestflickr.feature.list.presentation.PhotoList
import com.caique.aetnatestflickr.ui.design.AppTheme
import org.junit.Rule
import org.junit.Test

class SearchEmptyState {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun startsEmpty() {
        with(composeTestRule){
            setContent {
                AppTheme {
                    PhotoList(navController = rememberNavController())
                }
            }
            //Starts empty?
            onNodeWithTag("empty").assertExists()
        }
    }
}