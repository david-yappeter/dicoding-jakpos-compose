package myplayground.example.jakpost.ui.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import myplayground.example.jakpost.repository.NewsRepository
import myplayground.example.jakpost.ui.screens.home.HomeViewModel
import myplayground.example.jakpost.ui.screens.search.SearchViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: NewsRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}