package myplayground.example.jakpost.ui.screens.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import myplayground.example.jakpost.repository.NewsRepository
import myplayground.example.jakpost.ui.utils.capitalized

class HomeViewModel(private val repository: NewsRepository) : ViewModel() {
    val _selectedTabIndex: MutableState<Int> = mutableStateOf(0)
    val selectedTabIndex: State<Int> = _selectedTabIndex
    val TAB_LIST = TABS.all().map { it.toCapitalCase() }


    fun onTabClick(currentSelectedTab: Int) {
        _selectedTabIndex.value = currentSelectedTab
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