package myplayground.example.jakpost.ui.screens.news_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import myplayground.example.jakpost.model.News
import myplayground.example.jakpost.repository.NewsRepository
import myplayground.example.jakpost.ui.common.UiState

class NewsDetailViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<News>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<News>> get() = _uiState

    fun getNewsById(id: Int) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            newsRepository.getById(id).catch {
                _uiState.value = UiState.Error(it.message.toString())
            }.collect { news ->
                if (news == null) {
                    _uiState.value = UiState.NoData
                } else {
                    _uiState.value = UiState.Success(news)
                }
            }
        }
    }

}