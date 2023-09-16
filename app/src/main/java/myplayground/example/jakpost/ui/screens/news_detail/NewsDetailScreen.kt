package myplayground.example.jakpost.ui.screens.news_detail

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
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
import myplayground.example.jakpost.ui.components.shimmerBrush
import myplayground.example.jakpost.ui.theme.JakPostTheme
import myplayground.example.jakpost.ui.theme.Subtitle
import myplayground.example.jakpost.ui.utils.ViewModelFactory
import myplayground.example.jakpost.ui.utils.debugPlaceholder
import myplayground.example.jakpost.ui.utils.sampleNews

@Composable
fun NewsDetailScreen(
    modifier: Modifier = Modifier,
    newsId: Int,
    navigateBack: () -> Unit,
    viewModel: NewsDetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideNewsRepository(context = LocalContext.current),
            Injection.provideLocalNewsRepository(context = LocalContext.current),
            DatastoreSettings.getInstance(ds = LocalContext.current.dataStore),
        )
    )
) {
    val uiState by viewModel.uiState.collectAsState(initial = UiState.Loading)
    val isFavourite by viewModel.isFavourite

    var isLoading = false
    var news: News? = null
    when (uiState) {
        is UiState.Loading -> {
            isLoading = true

            viewModel.getNewsById(newsId)
        }

        is UiState.NoData -> {
            Text(text = "No data")
        }

        is UiState.Success -> {
            news = (uiState as UiState.Success<News>).data
        }

        is UiState.Error -> {
            Text(text = "Error")
        }
    }

    NewsDetailContent(
        modifier = modifier,
        isLoading = isLoading,
        news = news,
        onBackClick = navigateBack,
        toggleFavourite = viewModel::toggleFavourite,
        isFavourite = isFavourite,
    )
}

@Composable
fun NewsDetailContent(
    modifier: Modifier = Modifier,
    isFavourite: Boolean,
    onBackClick: () -> Unit,
    isLoading: Boolean,
    toggleFavourite: (news: News) -> Unit = {},
    news: News?,
) {
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            if (!isLoading) {
                FloatingActionButton(
                    onClick = {
                        if (news != null) {
                            toggleFavourite(news)
                        }
                    },
                    shape = MaterialTheme.shapes.large,
                ) {
                    Icon(
                        imageVector = if (isFavourite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favourite",
                        tint = MaterialTheme.colorScheme.onPrimary,
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { innerPadding ->
        Column(modifier = modifier.padding(innerPadding)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth()
                    .background(color = MaterialTheme.colorScheme.primary)
                    .padding(start = 12.dp, end = 12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Previous",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .size(36.dp)
                        .testTag("on_back_click")
                        .clickable {
                            onBackClick()
                        },
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = news?.category ?: "",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleLarge,
                )
            }
            Column(
                modifier = Modifier
                    .weight(1F)
                    .verticalScroll(rememberScrollState())
            ) {
                if (isLoading) {
                    NewsBlockSkeletonView()
                } else {
                    if (news != null) {
                        NewsBlock(news = news)
                    }
                }
            }
        }

    }
}

@Composable
fun NewsBlock(news: News) {
    Column(modifier = Modifier
        .padding(16.dp, 0.dp)
        .testTag("news_detail_block")) {
        Spacer(modifier = Modifier.height(12.dp))
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
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Author: ${news.detailPost?.author}",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineSmall,
        )
        Text(
            text = news.detailPost?.publishedAt ?: "",
            color = Subtitle,
            style = MaterialTheme.typography.headlineSmall,
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
    AsyncImage(
        model = news.detailPost?.image ?: "",
        contentDescription = "Thumbnail",
        placeholder = debugPlaceholder(
            debugPreview = R.drawable.sample_news_image_1
        ),
        contentScale = ContentScale.FillWidth,
        modifier = Modifier.fillMaxWidth(),
    )
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = news.detailPost?.imageDesc ?: "",
            color = Subtitle,
            style = MaterialTheme.typography.headlineSmall,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = news.detailPost?.postContent ?: "",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
fun NewsBlockSkeletonView() {
    val boxModifier = Modifier.clip(MaterialTheme.shapes.medium)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("news_detail_block_skeleton_view")
    ) {
        Column(modifier = Modifier.padding(16.dp, 0.dp)) {
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = boxModifier
                    .fillMaxWidth()
                    .height(25.dp)
                    .background(shimmerBrush()),
            )
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = boxModifier
                    .fillMaxWidth()
                    .height(25.dp)
                    .background(shimmerBrush()),
            )
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = boxModifier
                    .height(25.dp)
                    .width(80.dp)
                    .background(shimmerBrush()),
            )
            Spacer(modifier = Modifier.height(30.dp))
            Box(
                modifier = boxModifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .background(shimmerBrush()),
            )
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = boxModifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .background(shimmerBrush()),
            )
            Spacer(modifier = Modifier.height(20.dp))
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(shimmerBrush()),
        )
        Column(modifier = Modifier.padding(16.dp, 0.dp)) {
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = boxModifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .background(shimmerBrush()),
            )
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = boxModifier
                    .fillMaxWidth()
                    .height(20.dp)
                    .background(shimmerBrush()),
            )
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = boxModifier
                    .height(20.dp)
                    .width(80.dp)
                    .background(shimmerBrush()),
            )
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = boxModifier
                    .fillMaxWidth()
                    .height(25.dp)
                    .background(shimmerBrush()),
            )
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = boxModifier
                    .fillMaxWidth()
                    .height(25.dp)
                    .background(shimmerBrush()),
            )
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = boxModifier
                    .fillMaxWidth()
                    .height(25.dp)
                    .background(shimmerBrush()),
            )
            Spacer(modifier = Modifier.height(12.dp))
            Box(
                modifier = boxModifier
                    .height(25.dp)
                    .width(80.dp)
                    .background(shimmerBrush()),
            )
        }

    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun NewsDetailContentPreview() {
    JakPostTheme {
        NewsDetailContent(
            onBackClick = {},
            news = sampleNews[0],
            isLoading = false,
            isFavourite = true
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun NewsBlockSkeletonViewPreview() {
    JakPostTheme {
        NewsBlockSkeletonView()
    }
}
