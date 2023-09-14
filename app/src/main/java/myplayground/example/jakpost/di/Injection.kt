package myplayground.example.jakpost.di

import android.content.Context
import myplayground.example.jakpost.repository.FakeNewsRepository
import myplayground.example.jakpost.repository.NewsRepository

object Injection {
    fun provideNewsRepository(context: Context): NewsRepository {
        return FakeNewsRepository.getInstance(context)
    }
}