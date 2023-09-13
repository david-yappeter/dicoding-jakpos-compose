package myplayground.example.jakpost.repository

import kotlinx.coroutines.flow.Flow
import myplayground.example.jakpost.model.News

interface NewsRepository {
    fun fetchAll(search: String? = null): Flow<List<News>>
}