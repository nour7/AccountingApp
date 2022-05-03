package com.example.accountingapp.services

import com.example.accountingapp.store.database.RecordsDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn


class BudgetRepository private constructor(
    recordsDao: RecordsDao,
    dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    val recordsFlow = recordsDao.getRecords().flowOn(dispatcher).conflate()

    companion object {
        @Volatile private var instance: BudgetRepository? = null

        fun getInstance(recordsDao: RecordsDao) =
            instance ?: synchronized(this) {
                instance ?: BudgetRepository(recordsDao).also { instance = it }
            }
    }
}