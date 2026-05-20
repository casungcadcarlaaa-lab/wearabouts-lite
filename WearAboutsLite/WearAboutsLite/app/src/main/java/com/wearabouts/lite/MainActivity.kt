package com.wearabouts.lite

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.wearabouts.lite.data.local.AppDatabase
import com.wearabouts.lite.data.local.UserPreferences
import com.wearabouts.lite.data.repository.ClothingRepository
import com.wearabouts.lite.navigation.NavGraph
import com.wearabouts.lite.ui.theme.WearAboutsTheme
import com.wearabouts.lite.viewmodel.ClothingViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val database = AppDatabase.getDatabase(this)
        val repository = ClothingRepository(database.clothingDao(), database.historyDao())
        val userPreferences = UserPreferences(this)
        val viewModel = ViewModelProvider(
            this, 
            ClothingViewModel.Factory(repository, userPreferences)
        )[ClothingViewModel::class.java]

        setContent {
            val isDarkMode by viewModel.isDarkMode.collectAsState()
            
            WearAboutsTheme(darkTheme = isDarkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = androidx.compose.material3.MaterialTheme.colorScheme.background
                ) {
                    NavGraph(viewModel = viewModel)
                }
            }
        }
    }
}
