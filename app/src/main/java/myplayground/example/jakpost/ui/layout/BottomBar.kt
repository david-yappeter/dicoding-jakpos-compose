package myplayground.example.jakpost.ui.layout

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import myplayground.example.jakpost.ui.navigation.NavigationItem
import myplayground.example.jakpost.ui.navigation.Screen
import myplayground.example.jakpost.ui.theme.JakPostTheme

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = Screen.fromRoute(navBackStackEntry?.destination?.route)

    NavigationBar(
        modifier = modifier,
    ) {
        val navigationItems = listOf(
            NavigationItem(
                title = "Home",
                icon = Icons.Default.Home,
                screen = Screen.Home,
            ),
            NavigationItem(
                title = "Favourite",
                icon = Icons.Default.Favorite,
                screen = Screen.Favourite,
            ),
            NavigationItem(
                title = "Setting",
                icon = Icons.Default.Settings,
                screen = Screen.Setting,
            ),
        )


        navigationItems.map { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                        tint = MaterialTheme.colorScheme.primary,
                    )
                },
                label = {
                    Text(
                        item.title,
                        color = MaterialTheme.colorScheme.primary,
                    )
                },
                selected = currentScreen == item.screen,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BottomBarPreview() {
    JakPostTheme {
        BottomBar(navController = rememberNavController())
    }
}