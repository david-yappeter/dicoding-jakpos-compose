package myplayground.example.jakpost.ui.screens.home

import android.util.Log
import androidx.compose.runtime.MutableState
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
import myplayground.example.jakpost.ui.utils.capitalized

class HomeViewModel(private val repository: NewsRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<News>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<News>>> = _uiState

    private val _selectedTabIndex: MutableState<Int> = mutableStateOf(0)
    val selectedTabIndex: State<Int> = _selectedTabIndex
    val tabList = TABS.all()


    fun onTabClick(currentSelectedTab: Int) {
        _selectedTabIndex.value = currentSelectedTab

        viewModelScope.launch {
            _uiState.value = UiState.Loading
        }
    }

    fun fetchByTab() {
        val tab = tabList[_selectedTabIndex.value]
        viewModelScope.launch {
            val category = tab.toCapitalCase()

            repository.fetchByCategory(category).catch {
                _uiState.value = UiState.Error(it.message.toString())
            }.collect { news ->
                Log.i("FETCH SUCCCESSS", tab.toString())
                Log.i("FETCH SUCCCESSS", category.toString())
                Log.i("FETCH SUCCCESSS", news.toString())
                _uiState.value = UiState.Success(news)
            }
        }
    }

    companion object {

        enum class TABS {
            JAKARTA,
            SOCIETY,
            POLITICS,
            ARCHIPELAGO,
            PRESIDENTIAL_RACE,
            LEGISLATIVE_RACE,
            ELECTION_UPDATES;


            fun toCapitalCase(): String {
                return this.toString().split("_").joinToString(" ") { it.capitalized() }
            }

            companion object {
                fun all(): List<TABS> = enumValues<TABS>().toList()
            }
        }
    }

}