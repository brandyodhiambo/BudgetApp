package com.brandyodhiambo.budgetapp.utils

import android.annotation.SuppressLint
import androidx.compose.ui.graphics.Color
import com.brandyodhiambo.budgetapp.model.Category

const val FOOD = "Food & Beverage"
const val TRANSPORT = "Transport & Fuel"
const val CLOTHING = "Clothing"
const val TRANSACTION_FEES = "Transaction Fees"

val categories = listOf(
    Category(FOOD, Color(0xFF003251)),
    Category(TRANSPORT, Color(0xFF80BA27)),
    Category(CLOTHING, Color(0xFF4DD6EE)),
    Category(TRANSACTION_FEES, Color(0xFFFF7441)),
)

/**
 * Given a timestamp, return a formatted date
 * e.g 1633660800000 -> 7th October 2021
 */
@SuppressLint("SimpleDateFormat")
fun Long.timestampToDate(): String {
    return try {
        val date = java.util.Date(this)
        val formatter = java.text.SimpleDateFormat("d MMMM yyyy")
        formatter.format(date)
    } catch (e: Exception) {
        this.toString()
    }
}

/**
 * Given a timestamp, return a formatted date and time
 * e.g 1633660800000 -> June 07, 2021 12:00 AM
 */
@SuppressLint("SimpleDateFormat")
fun Long.timestampToDateTime(): String {
    return try {
        val date = java.util.Date(this)
        val formatter = java.text.SimpleDateFormat("MMM dd, yyyy hh:mm a")
        formatter.format(date)
    } catch (e: Exception) {
        this.toString()
    }
}
