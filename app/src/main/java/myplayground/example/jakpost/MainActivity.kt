package myplayground.example.jakpost

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import myplayground.example.jakpost.di.Injection
import myplayground.example.jakpost.local_storage.DatastoreSettings
import myplayground.example.jakpost.local_storage.dataStore
import myplayground.example.jakpost.ui.theme.JakPostTheme
import myplayground.example.jakpost.ui.utils.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private val themeViewModel: ThemeViewModel by viewModels {
        ViewModelFactory(
            Injection.provideNewsRepository(context = this),
            Injection.provideLocalNewsRepository(context = this),
            DatastoreSettings.getInstance(applicationContext.dataStore),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isDarkTheme = themeViewModel.isDarkMode.collectAsState(initial = false)

            JakPostTheme(
                darkTheme = isDarkTheme.value
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    JakPostApp()
                }
            }
        }
    }
}