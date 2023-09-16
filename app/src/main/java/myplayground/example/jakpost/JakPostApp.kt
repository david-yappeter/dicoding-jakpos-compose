package myplayground.example.jakpost

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import myplayground.example.jakpost.ui.layout.Appbar
import myplayground.example.jakpost.ui.layout.BottomBar
import myplayground.example.jakpost.ui.layout.NavigationDrawer
import myplayground.example.jakpost.ui.navigation.Screen
import myplayground.example.jakpost.ui.screens.about.AboutScreen
import myplayground.example.jakpost.ui.screens.favourite.FavouriteScreen
import myplayground.example.jakpost.ui.screens.home.HomeScreen
import myplayground.example.jakpost.ui.screens.news_detail.NewsDetailScreen
import myplayground.example.jakpost.ui.screens.search.SearchScreen
import myplayground.example.jakpost.ui.screens.setting.SettingScreen

@Composable
fun JakPostApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationDrawer(
        drawerState = drawerState,
        navController = navController,
    ) {

        Scaffold(
            modifier = modifier,
            topBar = {
                if (currentRoute != Screen.Search.route
                    && currentRoute != Screen.NewsDetail.route
                )
                    Appbar(
                        drawerState = drawerState,
                        navController = navController,
                    )
            },
            bottomBar = {
                if (currentRoute != Screen.Search.route
                    && currentRoute != Screen.NewsDetail.route
                    && currentRoute != Screen.About.route
                )
                    BottomBar(navController = navController)
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = Modifier.padding(innerPadding),
            ) {
                val navigateToNewsDetail: (Int) -> Unit = { id ->
                    navController.navigate(Screen.NewsDetail.createRoute(id))
                }

                composable(Screen.Home.route) {
                    HomeScreen(
                        navigateToNewsDetail = navigateToNewsDetail,
                    )
                }
                composable(Screen.Favourite.route) {
                    FavouriteScreen(
                        navigateToNewsDetail = navigateToNewsDetail,
                    )
                }
                composable(Screen.Setting.route) {
                    SettingScreen()
                }
                composable(Screen.Search.route) {
                    SearchScreen(
                        navigateBack = {
                            navController.navigateUp()
                        },
                        navigateToNewsDetail = navigateToNewsDetail,
                    )
                }
                composable(
                    Screen.NewsDetail.route,
                    arguments = listOf(navArgument("id") { type = NavType.IntType }),
                ) {
                    val id = it.arguments?.getInt("id") ?: -1
                    NewsDetailScreen(
                        newsId = id,
                        navigateBack = {
                            navController.navigateUp()
                        }
                    )
                }
                composable(Screen.About.route) {
                    AboutScreen(
                        navigateBack = {
                            navController.navigateUp()
                        },
                    )
                }
            }
        }
    }
}


