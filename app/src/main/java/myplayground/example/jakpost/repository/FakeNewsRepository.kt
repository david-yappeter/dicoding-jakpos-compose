package myplayground.example.jakpost.repository

import android.content.Context
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import myplayground.example.jakpost.R
import myplayground.example.jakpost.model.News
import myplayground.example.jakpost.utils.JsonHelper

class FakeNewsRepository(context: Context) : NewsRepository {
    private val newsList = getFakeNewsList(context).toMutableList()

    override fun fetchAll(search: String?): Flow<List<News>> {
        return flow {
            delay(1500)

            emit(newsList.filter {
                search == null || search == ""
                        || it.title.contains(search, ignoreCase = true)
            })
        }
    }

    override fun fetchByCategory(category: String): Flow<List<News>> {
        return flow {
            delay(1500)

            emit(newsList.filter {
                it.category == category
            })
        }
    }

    override fun getById(id: Int): Flow<News?> {
        return flow {
            delay(1500)

            emit(newsList.find { it.id == id })
        }
    }

    companion object {
        @Volatile
        private var instance: FakeNewsRepository? = null

        fun getInstance(context: Context): FakeNewsRepository = instance ?: synchronized(this) {
            FakeNewsRepository(context).apply {
                instance = this
            }
        }
    }
}

private fun getFakeNewsList(context: Context): List<News> =
    JsonHelper.mustDecodeFromFile(context, R.raw.fake_news_data)

