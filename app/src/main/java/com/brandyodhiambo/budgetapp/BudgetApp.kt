package com.brandyodhiambo.budgetapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BudgetApp: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}