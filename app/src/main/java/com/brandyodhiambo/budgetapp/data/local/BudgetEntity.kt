package com.brandyodhiambo.budgetapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budgets")
data class BudgetEntity(
    @PrimaryKey val allocatedAmount: Double, // Total allocated amount for the category
    val spentAmount: Double // Amount spent in the category
)