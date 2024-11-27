package com.brandyodhiambo.budgetapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBudget(budget: BudgetEntity)

    @Query("UPDATE budgets SET spentAmount = spentAmount + :amount")
    suspend fun updateSpentAmount(amount: Double)

    @Query("SELECT * FROM budgets")
    fun getTotalBudget(): Flow<BudgetEntity?>
}