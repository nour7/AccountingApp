package com.example.accountingapp.services

import com.example.accountingapp.store.database.Record
import com.example.accountingapp.store.database.RecordsDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import java.util.*


class BudgetRepository private constructor(
    private val recordsDao: RecordsDao,
    dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    val recordsFlow = recordsDao.getRecords().flowOn(dispatcher).conflate()

    suspend fun query(id: UUID) = recordsDao.query(id)
    suspend fun addRecord(record: Record) = recordsDao.add(record)
    suspend fun editRecord(record: Record) = recordsDao.update(record)

    companion object {
        @Volatile private var instance: BudgetRepository? = null

        fun getInstance(recordsDao: RecordsDao) =
            instance ?: synchronized(this) {
                instance ?: BudgetRepository(recordsDao).also { instance = it }
            }
    }
}