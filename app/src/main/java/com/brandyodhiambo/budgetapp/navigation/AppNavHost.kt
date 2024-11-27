package com.brandyodhiambo.budgetapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.brandyodhiambo.budgetapp.screen.account.AccountScreen
import com.brandyodhiambo.budgetapp.screen.add_budget.AddBudgetScreen
import com.brandyodhiambo.budgetapp.screen.add_budget.BudgetAddedScreen
import com.brandyodhiambo.budgetapp.screen.budgets.BudgetsScreen
import com.brandyodhiambo.budgetapp.screen.home.HomeScreen
import com.brandyodhiambo.budgetapp.screen.reports.ReportsScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Destinations.Home,
    ) {
        composable<Destinations.Home> {
            HomeScreen(
                navController = navController
            )
        }

        composable<Destinations.Budgets> {
            BudgetsScreen(
                navController = navController
            )
        }

        composable<Destinations.Reports> {
            ReportsScreen(
                navController = navController
            )
        }

        composable<Destinations.Account> {
            AccountScreen(
                navController = navController
            )
        }

        composable<Destinations.AddBudgetItem> {
            AddBudgetScreen(
                navController = navController
            )
        }

        composable<Destinations.BudgetAdded> { backStackEntry ->
            val budgetItemName = backStackEntry.toRoute<Destinations.BudgetAdded>().budgetItemName
            val amount = backStackEntry.toRoute<Destinations.BudgetAdded>().amount
            val date = backStackEntry.toRoute<Destinations.BudgetAdded>().date
            BudgetAddedScreen(
                navController = navController,
                budgetItemName = budgetItemName,
                amount = amount,
                date = date
            )
        }
    }
}