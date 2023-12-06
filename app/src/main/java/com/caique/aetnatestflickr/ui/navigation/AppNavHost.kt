package com.caique.aetnatestflickr.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.caique.aetnatestflickr.feature.list.presentation.DetailScreen
import com.caique.aetnatestflickr.feature.list.presentation.PhotoList
import com.caique.aetnatestflickr.ui.Screen
import com.caique.aetnatestflickr.ui.getDetailPhoto

@Composable
fun AppNavHost(
  navController: NavHostController,
  modifier: Modifier
) {
  NavHost(
    navController = navController,
    startDestination = Screen.HomeList.route,
    modifier = modifier
  ) {
    composable(Screen.HomeList.route) {
      PhotoList(navController, modifier)
    }
    composable(
        route = Screen.Detail.route,
        ) {
            val photo = navController.getDetailPhoto()
            photo?.let{ DetailScreen(photo) }
        }
  }
}