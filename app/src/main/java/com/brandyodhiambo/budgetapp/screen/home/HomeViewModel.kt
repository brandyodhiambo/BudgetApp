package com.brandyodhiambo.budgetapp.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.brandyodhiambo.budgetapp.data.local.BudgetEntity
import com.brandyodhiambo.budgetapp.data.repository.BudgetRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: BudgetRepository) : ViewModel() {
    val allTransactions = repository.allTransactions
    val remainingBudget = repository.getRemainingBudget().map {
        it.toString()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        "0.0",
    )

    val showSetMonthlyExpenseDialog = repository.getBudget().map {
        it?.allocatedAmount == null || it.allocatedAmount == 0.0
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        false,
    )

    fun setMonthlyExpense(monthlyExpenseAmount: String) = viewModelScope.launch {
        val budget = BudgetEntity(
            allocatedAmount = monthlyExpenseAmount.toDouble(),
            spentAmount = 0.0,
        )
        repository.addBudget(budget)
    }
}