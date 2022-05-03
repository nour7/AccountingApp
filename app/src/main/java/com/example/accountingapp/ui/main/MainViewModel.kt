package com.example.accountingapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.accountingapp.services.BudgetRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
class MainViewModelFactory(
    private val budgetRepository: BudgetRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = MainViewModel(budgetRepository) as T
}

class MainViewModel internal constructor(
    budgetRepository: BudgetRepository) : ViewModel() {
    val budgetRecords = budgetRepository.recordsFlow.asLiveData()

}