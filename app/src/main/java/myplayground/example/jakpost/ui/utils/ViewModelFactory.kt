package myplayground.example.jakpost.ui.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import myplayground.example.jakpost.ThemeViewModel
import myplayground.example.jakpost.local_storage.LocalStorageManager
import myplayground.example.jakpost.repository.LocalNewsRepository
import myplayground.example.jakpost.repository.NewsRepository
import myplayground.example.jakpost.ui.screens.favourite.FavouriteViewModel
import myplayground.example.jakpost.ui.screens.home.HomeViewModel
import myplayground.example.jakpost.ui.screens.news_detail.NewsDetailViewModel
import myplayground.example.jakpost.ui.screens.search.SearchViewModel
import myplayground.example.jakpost.ui.screens.setting.SettingViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val newsRepository: NewsRepository,
    private val localNewsRepository: LocalNewsRepository,
    private val localStorageManager: LocalStorageManager,
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(newsRepository) as T
        } else if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(newsRepository) as T
        } else if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return SettingViewModel(newsRepository, localStorageManager) as T
        } else if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            return ThemeViewModel.getInstance(localStorageManager) as T
        } else if (modelClass.isAssignableFrom(NewsDetailViewModel::class.java)) {
            return NewsDetailViewModel(newsRepository, localNewsRepository) as T
        } else if (modelClass.isAssignableFrom(FavouriteViewModel::class.java)) {
            return FavouriteViewModel(localNewsRepository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}