package myplayground.example.jakpost.ui.screens.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import myplayground.example.jakpost.database.FavouriteNewsEntity
import myplayground.example.jakpost.repository.LocalNewsRepository
import myplayground.example.jakpost.ui.common.UiState

class FavouriteViewModel(
    private val localNewsRepository: LocalNewsRepository,
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<FavouriteNewsEntity>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<FavouriteNewsEntity>>> = _uiState


    fun fetchAll() {
        viewModelScope.launch {
            localNewsRepository.fetchAll().catch {
                _uiState.value = UiState.Error(it.message.toString())
            }.collect { newsEntities ->
                if (newsEntities.isEmpty()) {
                    _uiState.value = UiState.NoData
                } else {
                    _uiState.value = UiState.Success(newsEntities)
                }
            }
        }
    }
}