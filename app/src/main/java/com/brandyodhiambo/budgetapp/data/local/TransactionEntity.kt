package com.brandyodhiambo.budgetapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String, // e.g., "KFC Westlands"
    val category: String, // e.g., "Eating-out"
    val amount: Double, // e.g., 780.00
    val date: Long // Unix timestamp for sorting and filtering
)