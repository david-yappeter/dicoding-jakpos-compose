package myplayground.example.jakpost.ui.screens.favourite

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import myplayground.example.jakpost.R
import myplayground.example.jakpost.database.FavouriteNewsEntity
import myplayground.example.jakpost.di.Injection
import myplayground.example.jakpost.local_storage.DatastoreSettings
import myplayground.example.jakpost.local_storage.dataStore
import myplayground.example.jakpost.ui.common.UiState
import myplayground.example.jakpost.ui.components.NoData
import myplayground.example.jakpost.ui.components.shimmerBrush
import myplayground.example.jakpost.ui.theme.JakPostTheme
import myplayground.example.jakpost.ui.theme.Subtitle
import myplayground.example.jakpost.ui.utils.ViewModelFactory
import myplayground.example.jakpost.ui.utils.debugPlaceholder

@Composable
fun FavouriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavouriteViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideNewsRepository(LocalContext.current),
            Injection.provideLocalNewsRepository(context = LocalContext.current),
            DatastoreSettings.getInstance(LocalContext.current.dataStore),
        )
    ),
    navigateToNewsDetail: (Int) -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()

    var isLoading = false
    var noData = false
    var newsEntities: List<FavouriteNewsEntity> = listOf()

    when (uiState) {
        is UiState.Loading -> {
            isLoading = true
            viewModel.fetchAll()
        }

        is UiState.Error -> {

        }

        is UiState.Success -> {
            newsEntities = (uiState as UiState.Success<List<FavouriteNewsEntity>>).data
        }

        is UiState.NoData -> {
            noData = true
        }
    }

    FavouriteContent(
        isLoading = isLoading,
        newsEntities = newsEntities,
        navigateToNewsDetail = navigateToNewsDetail,
        noData = noData,
    )
}

@Composable
fun FavouriteContent(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    noData: Boolean = false,
    newsEntities: List<FavouriteNewsEntity>,
    navigateToNewsDetail: (Int) -> Unit = {},
) {
    if (noData) {
        NoData()
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
        ) {
            if (isLoading) {
                items(5) {
                    FavouriteNewsBlockSkeletonView()
                }
            } else {
                items(newsEntities) { newsEntity ->
                    FavouriteNewsBlock(
                        newsEntity = newsEntity,
                        navigateToNewsDetail = navigateToNewsDetail,
                    )
                }
            }
        }
    }
}

@Composable
fun FavouriteNewsBlock(
    newsEntity: FavouriteNewsEntity,
    navigateToNewsDetail: (Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(bottom = 20.dp)
            .testTag("favourite_news_block")
            .clickable {
                navigateToNewsDetail(newsEntity.id)
            }) {
        AsyncImage(
            model = newsEntity.imageUrl ?: "",
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
                text = newsEntity.title,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = newsEntity.headline,
                color = Subtitle,
                style = MaterialTheme.typography.headlineSmall,
            )
        }
    }
}

@Composable
fun FavouriteNewsBlockSkeletonView() {
    val boxModifier = Modifier.clip(MaterialTheme.shapes.medium)
    Column(
        modifier = Modifier
            .padding(bottom = 20.dp)
            .testTag("favourite_skeleton_view"),
    ) {
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
fun FavouriteContentPreview() {
    JakPostTheme {
        FavouriteContent(
            isLoading = false,
            newsEntities = listOf(),
        )
    }
}
