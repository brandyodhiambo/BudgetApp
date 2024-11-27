package com.brandyodhiambo.budgetapp.model

import androidx.compose.ui.graphics.Color

data class Category(
    val name: String,
    val color: Color,
) {
    override fun toString(): String {
        return name
    }
}