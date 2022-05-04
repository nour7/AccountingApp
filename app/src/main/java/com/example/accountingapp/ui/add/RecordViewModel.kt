package com.example.accountingapp.ui.add
import android.util.Log
import androidx.lifecycle.*
import com.example.accountingapp.extensions.isValid
import com.example.accountingapp.services.BudgetRepository
import com.example.accountingapp.store.database.Record
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*

class RecordViewModelFactory(private val budgetRepository: BudgetRepository): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = RecordViewModel(budgetRepository) as T

}

data class AddRecordUiState(
    val amount: String? = null,
    val errors: List<String> = listOf(),
    var completed: Boolean = false)

val AddRecordUiState.savedButtonEnabled: Boolean get() = this.amount != null

class RecordViewModel internal constructor(private val budgetRepository: BudgetRepository) : ViewModel() {
    private val _uiState =  MutableStateFlow(AddRecordUiState())
    val uiState: LiveData<AddRecordUiState> = _uiState.asLiveData()
    private val _record: MutableStateFlow<Record?> = MutableStateFlow(null)
    val record: LiveData<Record?> = _record.asLiveData()


    fun queryRecord(id: String) {
        viewModelScope.launch {
            _record.value =  budgetRepository.query(UUID.fromString(id))
        }
    }

    fun addOrEditRecord(record: Record?, amount: String, info: String) {
        val expenseAmount = validateAmountValue(amount) ?: return
        when(record) {
            null -> addExpense(expenseAmount, info)
            else -> editExpense(expenseAmount, info, record )
        }
    }

    fun updateAmount(amount: CharSequence?) {
        _uiState.update {
            it.copy(amount = if(amount?.isEmpty() == true) null else amount?.toString())
        }
    }

    private fun addExpense(amount: Double, info: String) {
        viewModelScope.launch {
           val result =  budgetRepository.addRecord(Record(amount = amount, description = info, creationDate = Date(), editDate = Date()))
            when (result.isValid()) {
               true -> setCompleted()
                else -> throwError("Failed to add record to database!")
            }
        }
    }

    private fun editExpense(amount: Double, info: String, oldRecord: Record) {
        viewModelScope.launch {
            val result =  budgetRepository.editRecord(Record(recordId = oldRecord.recordId, amount = amount, description = info, creationDate = oldRecord.creationDate, editDate = Date()))
            when (result.isValid()) {
                true -> setCompleted()
                else -> throwError("Failed to edit record!")
            }
        }
    }

    private fun validateAmountValue(amount: String): Double?  {
        if (amount.toDoubleOrNull() == null) {
            _uiState.update {
                it.copy(errors = listOf("Amount must be valid!"))
            }
            return null
        }

        return amount.toDouble()
    }

    private fun setCompleted() = _uiState.update { it.copy(completed = true) }

    private fun throwError(msg: String) = _uiState.update { it.copy(errors = listOf(msg)) }


}