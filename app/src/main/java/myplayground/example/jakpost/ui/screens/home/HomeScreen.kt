package myplayground.example.jakpost.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import myplayground.example.jakpost.di.Injection
import myplayground.example.jakpost.ui.utils.ViewModelFactory

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideNewsRepository())
    )
) {
    Box(modifier = modifier) {

    }
}

