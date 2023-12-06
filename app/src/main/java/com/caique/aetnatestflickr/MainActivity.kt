package com.caique.aetnatestflickr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.caique.aetnatestflickr.ui.design.AppTheme
import com.caique.aetnatestflickr.ui.navigation.AppNavHost

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val navController = rememberNavController()
            AppTheme {
                Scaffold { paddingValues ->
                    AppNavHost(
                        navController = navController,
                        modifier = Modifier
                            .padding(paddingValues)
                    )
                }
            }
        }
    }
}
