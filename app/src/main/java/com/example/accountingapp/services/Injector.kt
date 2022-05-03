package com.example.accountingapp.services

import android.content.Context
import com.example.accountingapp.store.database.AppDatabase
import com.example.accountingapp.ui.main.MainViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi

interface ViewModelFactoryProvider {
    @OptIn(ExperimentalCoroutinesApi::class, kotlinx.coroutines.FlowPreview::class)
    fun provideMainViewModelFactory(context: Context): MainViewModelFactory
}

val injector: ViewModelFactoryProvider
    get() = currentInjector

@Volatile private var currentInjector: ViewModelFactoryProvider =
    DefaultViewModelProvider

private object DefaultViewModelProvider: ViewModelFactoryProvider {
    private fun getBudgetRepository(context: Context): BudgetRepository {
        return BudgetRepository.getInstance(
            recordsDao(context)
        )
    }

    private fun recordsDao(context: Context) =
        AppDatabase.getInstance(context.applicationContext).recordsDao()


    @OptIn(ExperimentalCoroutinesApi::class, kotlinx.coroutines.FlowPreview::class)
    override fun provideMainViewModelFactory(context: Context): MainViewModelFactory {
        val repository = getBudgetRepository(context)
        return MainViewModelFactory(repository)
    }
}