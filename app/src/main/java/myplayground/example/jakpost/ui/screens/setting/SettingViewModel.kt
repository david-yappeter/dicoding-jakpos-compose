package myplayground.example.jakpost.ui.screens.setting

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import myplayground.example.jakpost.ThemeViewModel
import myplayground.example.jakpost.local_storage.LocalStorageManager
import myplayground.example.jakpost.repository.NewsRepository

class SettingViewModel(
    private val repository: NewsRepository,
    private val localStorageManager: LocalStorageManager
) : ViewModel() {
}