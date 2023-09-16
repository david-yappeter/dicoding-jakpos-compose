package myplayground.example.jakpost.ui.screens.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import myplayground.example.jakpost.R
import myplayground.example.jakpost.di.Injection
import myplayground.example.jakpost.local_storage.DatastoreSettings
import myplayground.example.jakpost.local_storage.dataStore
import myplayground.example.jakpost.model.News
import myplayground.example.jakpost.ui.common.UiState
import myplayground.example.jakpost.ui.components.JakTabBar
import myplayground.example.jakpost.ui.components.shimmerBrush
import myplayground.example.jakpost.ui.theme.JakPostTheme
import myplayground.example.jakpost.ui.theme.Subtitle
import myplayground.example.jakpost.ui.utils.ViewModelFactory
import myplayground.example.jakpost.ui.utils.debugPlaceholder
import myplayground.example.jakpost.ui.utils.sampleNews

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideNewsRepository(LocalContext.current),
            Injection.provideLocalNewsRepository(context = LocalContext.current),
            DatastoreSettings.getInstance(LocalContext.current.dataStore),
        )
    ),
    navigateToNewsDetail: (Int) -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedTabIndex by viewModel.selectedTabIndex

    var isLoading = false
    var news: List<News> = listOf()

    when (uiState) {
        is UiState.Loading -> {
            isLoading = true
            viewModel.fetchByTab()
        }

        is UiState.Error -> {
        }

        is UiState.Success -> {
            news = (uiState as UiState.Success<List<News>>).data
        }

        else -> {}
    }

    HomeContent(
        isLoading = isLoading,
        modifier = modifier,
        selectedTabIndex = selectedTabIndex,
        tabs = viewModel.tabList.map { it.toCapitalCase().uppercase() },
        onTabClick = viewModel::onTabClick,
        listNews = news,
        navigateToNewsDetail = navigateToNewsDetail,
    )
}

@Composable
fun HomeContent(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    selectedTabIndex: Int,
    tabs: List<String>,
    onTabClick: (Int) -> Unit,
    listNews: List<News> = listOf(),
    navigateToNewsDetail: (Int) -> Unit = {},
) {
    Column(
        modifier = modifier,
    ) {
        JakTabBar(
            selectedTabIndex = selectedTabIndex,
            tabs = tabs,
            onTabClick = onTabClick,
            disabled = isLoading,
        )
        LazyColumn(
            modifier = Modifier.weight(1F),
        ) {
            if (isLoading) {
                items(5) {
                    HomeNewsBlockSkeletonView()
                }
            } else {
                items(listNews) { news ->
                    HomeNewsBlock(
                        news = news,
                        navigateToNewsDetail = navigateToNewsDetail,
                    )
                }
            }
        }
    }
}

@Composable
fun HomeNewsBlock(
    news: News,
    navigateToNewsDetail: (Int) -> Unit = {},
) {
    Column(modifier = Modifier
        .padding(bottom = 20.dp)
        .clickable {
            navigateToNewsDetail(news.id)
        }) {
        AsyncImage(
            model = news.detailPost?.image ?: "",
            contentDescription = "Thumbnail",
            placeholder = debugPlaceholder(
                debugPreview = R.drawable.sample_news_image_1
            ),
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth(),
        )
        Column(
            modifier = Modifier.padding(12.dp),
        ) {
            Text(
                text = news.title,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = news.headline,
                color = Subtitle,
                style = MaterialTheme.typography.headlineSmall,
            )
        }
    }
}

@Composable
fun HomeNewsBlockSkeletonView() {
    val boxModifier = Modifier.clip(MaterialTheme.shapes.medium)
    Column(modifier = Modifier.padding(bottom = 20.dp)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(shimmerBrush())
        )
        Column(
            modifier = Modifier.padding(12.dp),
        ) {
            Box(
                modifier = boxModifier
                    .fillMaxWidth()
                    .height(25.dp)
                    .background(shimmerBrush())
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = boxModifier
                    .fillMaxWidth()
                    .height(25.dp)
                    .background(shimmerBrush())
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = boxModifier
                    .width(70.dp)
                    .height(25.dp)
                    .background(shimmerBrush())
            )
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = boxModifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .background(shimmerBrush())
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = boxModifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .background(shimmerBrush())
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = boxModifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .background(shimmerBrush())
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = boxModifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .background(shimmerBrush())
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = boxModifier
                    .width(70.dp)
                    .height(12.dp)
                    .background(shimmerBrush())
            )
        }
    }
}


@Preview(showBackground = true, device = Devices.PIXEL_4)
@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HomeContentPreview() {
    JakPostTheme {
        HomeContent(
            isLoading = false,
            selectedTabIndex = 0,
            tabs = listOf("Indonesia", "Politics", "Society"),
            onTabClick = {},
            listNews = sampleNews,
        )
    }
}


@Preview(showBackground = true, device = Devices.PIXEL_4)
@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HomeContentLoadingPreview() {
    JakPostTheme {
        HomeContent(
            isLoading = true,
            selectedTabIndex = 0,
            tabs = listOf("Indonesia", "Politics", "Society"),
            onTabClick = {},
        )
    }
}