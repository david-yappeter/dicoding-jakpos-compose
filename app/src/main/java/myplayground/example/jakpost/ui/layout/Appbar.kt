package myplayground.example.jakpost.ui.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import myplayground.example.jakpost.R
import myplayground.example.jakpost.ui.navigation.Screen
import myplayground.example.jakpost.ui.theme.JakPostTheme

@Composable
fun Appbar(
    navController: NavHostController
) {
    Box(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.primary)
            .padding(start = 12.dp, end = 12.dp)
    ) {
        Icon(
            imageVector = Icons.Outlined.Menu,
            contentDescription = "Menu",
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.CenterStart)
        )
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.Center),
        )
        Icon(
            imageVector = Icons.Outlined.Search,
            contentDescription = "Menu",
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.CenterEnd)
                .clickable {
                    navController.navigate(Screen.Search.route)
                }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppbarPreview() {
    JakPostTheme {
        Appbar(rememberNavController())
    }
}
