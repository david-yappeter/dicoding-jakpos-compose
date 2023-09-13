package myplayground.example.jakpost.di

import myplayground.example.jakpost.repository.FakeNewsRepository
import myplayground.example.jakpost.repository.NewsRepository

object Injection {
    fun provideNewsRepository(): NewsRepository {
        return FakeNewsRepository.getInstance()
    }
}