package myplayground.example.jakpost.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favourite : Screen("favourite")
    object Search : Screen("search")
    object Setting : Screen("setting")
    object NewsDetail : Screen("news/{id}") {
        fun createRoute(id: Int) = "news/$id"
    }
}