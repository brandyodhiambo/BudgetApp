package com.brandyodhiambo.budgetapp.navigation

import kotlinx.serialization.Serializable

sealed class Destinations {
    @Serializable
    object Home

    @Serializable
    object Budgets

    @Serializable
    object Reports

    @Serializable
    object Account

    @Serializable
    object AddBudgetItem

    @Serializable
    data class BudgetAdded(
        val budgetItemName: String,
        val amount: String,
        val date: String,
    )
}