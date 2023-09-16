package myplayground.example.jakpost.ui.screens.about

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import myplayground.example.jakpost.di.Injection
import myplayground.example.jakpost.local_storage.DatastoreSettings
import myplayground.example.jakpost.local_storage.dataStore
import myplayground.example.jakpost.ui.theme.JakPostTheme
import myplayground.example.jakpost.ui.utils.ViewModelFactory

@Composable
fun AboutScreen(
    navigateBack: () -> Unit,
    viewModel: AboutViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideNewsRepository(LocalContext.current),
            Injection.provideLocalNewsRepository(LocalContext.current),
            DatastoreSettings.getInstance(LocalContext.current.dataStore),
        )
    )
) {

    AboutContent()
}

@Composable
fun AboutContent() {

}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun AboutContentPreview() {
    JakPostTheme {
        AboutContent()
    }
}
