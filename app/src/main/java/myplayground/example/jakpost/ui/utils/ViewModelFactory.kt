package myplayground.example.jakpost.ui.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import myplayground.example.jakpost.ThemeViewModel
import myplayground.example.jakpost.local_storage.LocalStorageManager
import myplayground.example.jakpost.repository.NewsRepository
import myplayground.example.jakpost.ui.screens.about.AboutViewModel
import myplayground.example.jakpost.ui.screens.home.HomeViewModel
import myplayground.example.jakpost.ui.screens.news_detail.NewsDetailViewModel
import myplayground.example.jakpost.ui.screens.search.SearchViewModel
import myplayground.example.jakpost.ui.screens.setting.SettingViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val repository: NewsRepository,
    private val localStorageManager: LocalStorageManager
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(repository, localStorageManager) as T
        } else if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            return ThemeViewModel.getInstance(localStorageManager) as T
        } else if (modelClass.isAssignableFrom(NewsDetailViewModel::class.java)) {
            return NewsDetailViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(AboutViewModel::class.java)) {
            return AboutViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}