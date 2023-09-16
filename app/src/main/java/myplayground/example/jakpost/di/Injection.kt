package myplayground.example.jakpost.di

import android.content.Context
import myplayground.example.jakpost.database.FavouriteNewsDatabase
import myplayground.example.jakpost.repository.FakeNewsRepository
import myplayground.example.jakpost.repository.LocalNewsRepository
import myplayground.example.jakpost.repository.NewsRepository
import myplayground.example.jakpost.repository.RoomLocalNewsRepository
import myplayground.example.jakpost.ui.utils.AppExecutors

object Injection {
    fun provideNewsRepository(context: Context): NewsRepository {
        return FakeNewsRepository.getInstance(context)
    }
    fun provideLocalNewsRepository(context: Context): LocalNewsRepository {
        val database = FavouriteNewsDatabase.getDatabase(context)
        val dao = database.favouriteNewsDao()
        val appExecutors = AppExecutors()
        return RoomLocalNewsRepository.getInstance(dao, appExecutors)
    }
}