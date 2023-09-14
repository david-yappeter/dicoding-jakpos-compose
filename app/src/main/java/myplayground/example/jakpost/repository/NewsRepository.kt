package myplayground.example.jakpost.repository

import kotlinx.coroutines.flow.Flow
import myplayground.example.jakpost.model.News

interface NewsRepository {
    fun fetchAll(search: String? = null): Flow<List<News>>

    fun fetchByCategory(category: String): Flow<List<News>>

    fun getById(id: Int): Flow<News?>
}