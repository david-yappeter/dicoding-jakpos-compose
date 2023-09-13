package myplayground.example.jakpost.ui.screens.search

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import myplayground.example.jakpost.R
import myplayground.example.jakpost.di.Injection
import myplayground.example.jakpost.model.News
import myplayground.example.jakpost.ui.common.UiState
import myplayground.example.jakpost.ui.components.Search
import myplayground.example.jakpost.ui.components.shimmerBrush
import myplayground.example.jakpost.ui.theme.JakPostTheme
import myplayground.example.jakpost.ui.theme.LightStroke
import myplayground.example.jakpost.ui.theme.Subtitle
import myplayground.example.jakpost.ui.utils.ViewModelFactory
import myplayground.example.jakpost.ui.utils.debugPlaceholder

val sampleNews = listOf(
    News(
        link = "https://jakpost.vercel.app/api/detailpost/indonesia/2023/07/10/jakarta-police-cling-to-staggered-working-hours-plan-despite-skepticism",
        title = "Jakarta Police cling to staggered working hours plan despite skepticism",
        image = "https://img.jakpost.net/c/2023/02/01/2023_02_01_135079_1675230408._thumbnail.jpg",
        headline = "The Jakarta Police have continued to advocate for staggered working hours to alleviate Jakarta's perennial traffic woes, even after failing to convince workers and employers to alter their schedules last year.",
        category = "Jakarta",
        publishedAt = "2 months ago",
        premiumBadge = "premium",
    ),
    News(
        link = "https://jakpost.vercel.app/api/detailpost/indonesia/2023/07/10/jakarta-police-cling-to-staggered-working-hours-plan-despite-skepticism",
        title = "Jakarta Police cling to staggered working hours plan despite skepticism",
        image = "https://img.jakpost.net/c/2023/02/01/2023_02_01_135079_1675230408._thumbnail.jpg",
        headline = "The Jakarta Police have continued to advocate for staggered working hours to alleviate Jakarta's perennial traffic woes, even after failing to convince workers and employers to alter their schedules last year.",
        category = "Jakarta",
        publishedAt = "2 months ago",
        premiumBadge = "premium",
    ),
    News(
        link = "https://jakpost.vercel.app/api/detailpost/indonesia/2023/07/10/jakarta-police-cling-to-staggered-working-hours-plan-despite-skepticism",
        title = "Jakarta Police cling to staggered working hours plan despite skepticism",
        image = "https://img.jakpost.net/c/2023/02/01/2023_02_01_135079_1675230408._thumbnail.jpg",
        headline = "The Jakarta Police have continued to advocate for staggered working hours to alleviate Jakarta's perennial traffic woes, even after failing to convince workers and employers to alter their schedules last year.",
        category = "Jakarta",
        publishedAt = "2 months ago",
        premiumBadge = "premium",
    ),
)

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideNewsRepository())
    ),
    navigateBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState(initial = UiState.Loading)
    val query by viewModel.query


    when (uiState) {
        is UiState.Loading -> {
            SearchContent(
                isLoading = true,
                listNews = listOf(),
                modifier = modifier,
                onBackClick = navigateBack,
                query = query,
                onQueryChange = viewModel::onQueryChange,
                onSearch = viewModel::onSearch
            )
        }

        is UiState.Success -> {
            SearchContent(
                isLoading = false,
                listNews = (uiState as UiState.Success).data,
                modifier = modifier,
                onBackClick = navigateBack,
                query = query,
                onQueryChange = viewModel::onQueryChange,
                onSearch = viewModel::onSearch
            )
        }

        is UiState.Error -> {}
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SearchContentPreview() {
    JakPostTheme {
        SearchContent(
            isLoading = false,
            listNews = sampleNews,
            modifier = Modifier,
            query = "",
            onQueryChange = {},
            onSearch = {}
        )
    }
}

@Composable
fun SearchContent(
    modifier: Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    isLoading: Boolean,
    listNews: List<News>,
    onBackClick: () -> Unit = {}
) {
    Column(
        modifier = modifier,
    ) {

        Row(
            modifier = Modifier
                .height(80.dp)
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.primary)
                .padding(start = 12.dp, end = 12.dp)
        ) {
            Icon(imageVector = Icons.Default.ArrowBack,
                contentDescription = "Previous",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(36.dp)
                    .clickable {
                        onBackClick()
                    })
            Spacer(modifier = Modifier.width(20.dp))
            Search(
                modifier = Modifier.align(Alignment.CenterVertically),
                query = query,
                onQueryChange = onQueryChange,
                onSearch = onSearch,
            )
        }
        Box(modifier = Modifier.weight(1F)) {
            LazyColumn {
                if (isLoading) {
                    items(5) {
                        SkeletonLoadingItem()
                        Divider(
                            color = LightStroke,
                            thickness = 1.dp,
                            modifier = Modifier
                                .padding(start = 12.dp, end = 12.dp)
                                .fillMaxWidth()
                        )
                    }
                } else {
                    items(listNews) { news ->
                        NewsBlock(news)
                        Divider(
                            color = LightStroke,
                            thickness = 1.dp,
                            modifier = Modifier
                                .padding(start = 12.dp, end = 12.dp)
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun NewsBlock(news: News) {
    Row(
        modifier = Modifier.padding(20.dp, 16.dp)
    ) {
        AsyncImage(
            model = news.image,
            contentDescription = "Content",
            placeholder = debugPlaceholder(R.drawable.sample_news_image_1),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(70.dp)
                .fillMaxHeight()
        )
        Spacer(modifier = Modifier.width(20.dp))
        Column {
            Text(
                text = news.title,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = news.publishedAt,
                color = Subtitle,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun NewsBlockPreview() {
    JakPostTheme {
        NewsBlock(news = sampleNews[0])
    }
}

@Composable
fun SkeletonLoadingItem() {
    Row(
        modifier = Modifier.padding(20.dp, 16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(70.dp)
                .fillMaxHeight()
                .clip(MaterialTheme.shapes.medium)
                .background(shimmerBrush())
        )
        Spacer(modifier = Modifier.width(20.dp))
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(shimmerBrush())
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .width(70.dp)
                    .height(10.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(shimmerBrush())
            )
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SkeletonLoadingItemPreview() {
    JakPostTheme {
        SkeletonLoadingItem()
    }
}
