package com.brandyodhiambo.budgetapp.navigation

import com.brandyodhiambo.budgetapp.R

enum class BottomNavigation(
    val label: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val route: Any,
) {
    Home(
        label = "Home",
        selectedIcon = R.drawable.ic_home,
        unselectedIcon = R.drawable.ic_home,
        route = Destinations.Home
    ),
    Budgets(
        label = "Budgets",
        selectedIcon = R.drawable.ic_budgets,
        unselectedIcon = R.drawable.ic_budgets,
        route = Destinations.Budgets
    ),
    Reports(
        label = "Reports",
        selectedIcon = R.drawable.ic_reports,
        unselectedIcon = R.drawable.ic_reports,
        route = Destinations.Reports
    ),
    Account(
        label = "Account",
        selectedIcon = R.drawable.ic_account,
        unselectedIcon = R.drawable.ic_account,
        route = Destinations.Account
    );
}