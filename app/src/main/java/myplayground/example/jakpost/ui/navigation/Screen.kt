package myplayground.example.jakpost.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favourite : Screen("favourite")
    object Search : Screen("search")
    object Setting : Screen("setting")
    object About : Screen("about")
    object NewsDetail : Screen("news/{id}") {
        fun createRoute(id: Int) = "news/$id"
    }

    companion object {
        fun fromRoute(route: String?): Screen {
            return when (route) {
                "home" -> Home
                "favourite" -> Favourite
                "setting" -> Setting
                else -> Home
            }
        }
    }
}