package myplayground.example.jakpost.ui.screens.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import myplayground.example.jakpost.di.Injection
import myplayground.example.jakpost.local_storage.DatastoreSettings
import myplayground.example.jakpost.local_storage.dataStore
import myplayground.example.jakpost.ui.components.JakTabBar
import myplayground.example.jakpost.ui.theme.JakPostTheme
import myplayground.example.jakpost.ui.utils.ViewModelFactory

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideNewsRepository(LocalContext.current),
            DatastoreSettings.getInstance(LocalContext.current.dataStore),
        )
    )
) {
    val selectedTabIndex by viewModel.selectedTabIndex

    HomeContent(
        modifier = modifier,
        selectedTabIndex = selectedTabIndex,
        tabs = viewModel.TAB_LIST,
        onTabClick = viewModel::onTabClick,
    )
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    selectedTabIndex: Int,
    tabs: List<String>,
    onTabClick: (Int) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        JakTabBar(
            selectedTabIndex = selectedTabIndex,
            tabs = tabs,
            onTabClick = onTabClick,
        )
        LazyColumn (
            modifier = Modifier.weight(1F),
        ){

        }
    }
}


@Preview(showBackground = true, device = Devices.PIXEL_4)
@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HomeContentPreview() {
    JakPostTheme {
        HomeContent(
            selectedTabIndex = 0,
            tabs = listOf("Indonesia", "Politics", "Society"),
            onTabClick = {},
        )
    }
}

