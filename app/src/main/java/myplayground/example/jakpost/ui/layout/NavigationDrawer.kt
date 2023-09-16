package myplayground.example.jakpost.ui.layout

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import myplayground.example.jakpost.ui.navigation.Screen
import myplayground.example.jakpost.ui.theme.JakPostTheme

data class AppDrawerItemInfo<T>(
    val drawerOption: T,
    val title: String,
    val drawable: @Composable () -> Unit,
    val description: String,
    val testTag: String,
)

object DrawerParams {
    val drawerButtons: ArrayList<AppDrawerItemInfo<Screen>> = arrayListOf(
        AppDrawerItemInfo(
            Screen.Home,
            "Home",
            {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "home_page",
                    modifier = Modifier.size(28.dp),
                    tint = MaterialTheme.colorScheme.primary,
                )
            },
            "Home menu",
            testTag = "navigation_item_home",
        ),
        AppDrawerItemInfo(
            Screen.About,
            "About",
            {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "about_page",
                    modifier = Modifier.size(28.dp),
                    tint = MaterialTheme.colorScheme.primary,
                )
            },
            "About menu",
            testTag = "navigation_item_about",
        ),
    )
}

@Composable
fun NavigationDrawer(
    drawerState: DrawerState,
    navController: NavHostController,
    content: @Composable () -> Unit,
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawerContent(
                drawerState = drawerState,
                menuItems = DrawerParams.drawerButtons,
                defaultPick = Screen.Home
            ) { onUserPickedOption ->
                val navigateTo = fun(route: String) {
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
                when (onUserPickedOption) {
                    Screen.Home, Screen.About -> {
                        navigateTo(onUserPickedOption.route)
                    }


                    else -> {}
                }
            }
        },
        content = content,
    )
}

@Composable
fun <T> AppDrawerContent(
    drawerState: DrawerState,
    menuItems: ArrayList<AppDrawerItemInfo<T>>,
    defaultPick: T,
    onClick: (T) -> Unit
) {
    // default home destination to avoid duplication
    var currentPick by remember { mutableStateOf(defaultPick) }
    val coroutineScope = rememberCoroutineScope()

    ModalDrawerSheet {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // column of options to pick from for user
                LazyColumn(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1F)
                        .padding(horizontal = 8.dp),
                ) {
                    // generates on demand the required composables
                    items(menuItems) { item ->
                        // custom UI representation of the button
                        AppDrawerItem(item = item) { navOption ->

                            // if it is the same - ignore the click
                            if (currentPick == navOption) {
                                return@AppDrawerItem
                            }

                            currentPick = navOption

                            // close the drawer after clicking the option
                            coroutineScope.launch {
                                drawerState.close()
                            }

                            // navigate to the required screen
                            onClick(navOption)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun <T> AppDrawerItem(item: AppDrawerItemInfo<T>, onClick: (options: T) -> Unit) =
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(item.testTag)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(
                    color = MaterialTheme.colorScheme.onPrimary,
                ),
                onClick = {
                    onClick(item.drawerOption)
                }
            ),
        shape = RoundedCornerShape(50),
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(0.dp, 16.dp),
        ) {

            item.drawable()

            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
            )
        }
    }

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun NavigationDrawerPreview() {
    JakPostTheme {
        NavigationDrawer(
            drawerState = rememberDrawerState(initialValue = DrawerValue.Open),
            navController = rememberNavController(),
        ) {

        }
    }
}