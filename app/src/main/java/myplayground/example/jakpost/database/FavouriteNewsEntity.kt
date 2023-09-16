package myplayground.example.jakpost.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import myplayground.example.jakpost.model.News

@Entity(
    tableName = "favourite_news"
)

data class FavouriteNewsEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "image_url")
    var imageUrl: String,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "headline")
    var headline: String,
) {
    companion object {
        fun fromNews(news: News): FavouriteNewsEntity = FavouriteNewsEntity(
            id = news.id,
            imageUrl = news.detailPost?.image ?: "",
            title = news.title,
            headline = news.headline,
        )
    }
}