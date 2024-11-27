package com.brandyodhiambo.budgetapp.screen.add_budget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.brandyodhiambo.budgetapp.data.local.TransactionEntity
import com.brandyodhiambo.budgetapp.data.repository.BudgetRepository
import com.brandyodhiambo.budgetapp.utils.timestampToDate
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddBudgetViewModel @Inject constructor(
    private val repository: BudgetRepository
) : ViewModel() {
    private val _eventFlow = Channel<AddBudgetEvent>(Channel.UNLIMITED)
    val eventFlow = _eventFlow.receiveAsFlow()

    fun addTransaction(
        selectedCategory: String,
        itemName: String,
        allocationAmount: String
    ) {
        viewModelScope.launch {
            if (selectedCategory.isBlank() || itemName.isBlank() || allocationAmount.isBlank()) {
                _eventFlow.trySend(AddBudgetEvent.SnackbarEvent("Please fill in all fields"))
                return@launch
            }

            val transaction = TransactionEntity(
                category = selectedCategory,
                amount = allocationAmount.toDouble(),
                name = itemName,
                date = System.currentTimeMillis()
            )
            repository.addTransaction(transaction)

            _eventFlow.trySend(
                AddBudgetEvent.NavigateSuccessScreen(
                    itemName = itemName,
                    amount = allocationAmount,
                    date = transaction.date.timestampToDate(),
                )
            )
        }
    }
}

sealed class AddBudgetEvent {
    data class NavigateSuccessScreen(
        val itemName: String,
        val amount: String,
        val date: String
    ) : AddBudgetEvent()

    data class SnackbarEvent(val message: String) : AddBudgetEvent()
}