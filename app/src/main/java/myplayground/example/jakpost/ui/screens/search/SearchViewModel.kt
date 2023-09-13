package myplayground.example.jakpost.ui.screens.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import myplayground.example.jakpost.model.News
import myplayground.example.jakpost.repository.NewsRepository
import myplayground.example.jakpost.ui.common.UiState

class SearchViewModel(private val repository: NewsRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<News>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<News>>> get() = _uiState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    init {
        fetchAll()
    }

    fun onQueryChange(newQuery: String) {
        _query.value = newQuery
    }

    fun onSearch(q: String) {
        fetchAll(q)
    }

    fun fetchAll(search: String = "") {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.fetchAll(search).catch {
                _uiState.value = UiState.Error(it.message.toString())
            }.collect { news ->
                _uiState.value = UiState.Success(news)
            }
        }
    }
}