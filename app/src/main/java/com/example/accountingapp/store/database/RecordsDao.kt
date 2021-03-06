package com.example.accountingapp.store.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.*

/** Record database table
 */
@Entity(tableName = "records")
data class Record(
    @PrimaryKey @ColumnInfo(name = "id") val recordId: UUID = UUID.randomUUID(),
    val amount: Double,
    val description: String?,
    val creationDate: Date?,
    val editDate: Date?
)

/** Record database DAO
 */
@Dao
interface RecordsDao {
    @Query("Select * from records order by creationDate")
    fun getRecords(): Flow<List<Record>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun add(record: Record): Long

    @Update
    suspend fun update(record: Record): Int

    @Query("Select * from records where id = :id limit 1")
    suspend fun query(id: UUID): Record?

    @Query("Select sum(amount) from records")
    fun sumAmount(): Flow<Double>
}