package com.caique.aetnatestflickr.ui

import android.os.Bundle
import androidx.navigation.NavController
import com.caique.aetnatestflickr.data.model.PhotoItem
import com.caique.aetnatestflickr.util.toJson
import java.net.URLEncoder

sealed class Screen(
  val route: String,
) {
  data object HomeList : Screen("home_list")

  data object Detail: Screen("detail/{photo}")
}

fun NavController.navigateToDetail(photoItem: PhotoItem) {
  currentBackStackEntry?.savedStateHandle?.set("photo", photoItem)
  navigate("detail/$photoItem")
}

fun NavController.getDetailPhoto() = previousBackStackEntry?.savedStateHandle
  ?.get<PhotoItem>("photo")