package myplayground.example.jakpost.ui.screens.setting

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import myplayground.example.jakpost.ThemeViewModel
import myplayground.example.jakpost.di.Injection
import myplayground.example.jakpost.local_storage.DatastoreSettings
import myplayground.example.jakpost.local_storage.dataStore
import myplayground.example.jakpost.ui.components.JakSwitch
import myplayground.example.jakpost.ui.theme.JakPostTheme
import myplayground.example.jakpost.ui.utils.ViewModelFactory

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    themeViewModel: ThemeViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideNewsRepository(LocalContext.current),
            Injection.provideLocalNewsRepository(context = LocalContext.current),
            DatastoreSettings.getInstance(LocalContext.current.dataStore),
        )
    ),
) {
    val isDarkMode by themeViewModel.isDarkMode.collectAsState()
    SettingContent(
        modifier = modifier,
        isDarkModeChecked = isDarkMode,
        onDarkModeChange = themeViewModel::setDarkMode,
    )
}

@Composable
fun SettingContent(
    modifier: Modifier = Modifier,
    isDarkModeChecked: Boolean,
    onDarkModeChange: (Boolean) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(14.dp, 10.dp)
    ) {
        JakSwitch(
            checked = isDarkModeChecked,
            onCheckedChange = onDarkModeChange,
            text = "Dark Mode"
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SettingContentPreview() {
    JakPostTheme {
        SettingContent(
            isDarkModeChecked = true,
            onDarkModeChange = {}
        )
    }
}

