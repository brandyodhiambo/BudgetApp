package com.brandyodhiambo.budgetapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FabPosition
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.brandyodhiambo.budgetapp.navigation.BottomNavigation
import com.brandyodhiambo.budgetapp.navigation.Destinations
import com.brandyodhiambo.budgetapp.navigation.AppNavHost
import com.brandyodhiambo.budgetapp.ui.theme.BudgetAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BudgetAppTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                    ?: Destinations.Home::class.qualifiedName.orEmpty()
                val addBudgetItem =
                    Destinations.AddBudgetItem::class.qualifiedName?.substringBefore("?")
                val showBottomNavigation =
                    // currentRoute in BottomNavigation.entries.map { it.route::class.qualifiedName } || currentRoute == addBudgetItem
                    true

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButtonPosition = FabPosition.Center,
                    contentWindowInsets = WindowInsets.navigationBars.only(WindowInsetsSides.Horizontal),
                    floatingActionButton = {
                        Image(
                            modifier = Modifier
                                .offset(y = 80.dp)
                                .size(56.dp)
                                .clickable(MutableInteractionSource(), null) {
                                    navController.navigate(Destinations.AddBudgetItem)
                                },
                            painter = painterResource(id = R.drawable.add_button),
                            contentDescription = "Add Item",
                        )
                    },
                    bottomBar = {
                        if (showBottomNavigation) {
                            Column(
                            ) {
                                HorizontalDivider(
                                    thickness = .5.dp,
                                )
                                NavigationBar(
                                    tonalElevation = 0.dp,
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                ) {
                                    BottomNavigation.entries
                                        .forEachIndexed { index, navigationItem ->
                                            val isSelected by remember(currentRoute) {
                                                derivedStateOf { currentRoute == navigationItem.route::class.qualifiedName }
                                            }

                                            NavigationBarItem(
                                                modifier = Modifier
                                                    .offset(
                                                        x = when (index) {
                                                            0 -> 0.dp
                                                            1 -> (-24).dp
                                                            2 -> 24.dp
                                                            3 -> 0.dp
                                                            else -> 0.dp
                                                        },
                                                    ),
                                                selected = isSelected,
                                                icon = {
                                                    Column(
                                                        horizontalAlignment = Alignment.CenterHorizontally,
                                                        verticalArrangement = Arrangement.spacedBy(4.dp)
                                                    ) {
                                                        Icon(
                                                            modifier = Modifier.size(16.dp),
                                                            painter = painterResource(if (isSelected) navigationItem.selectedIcon else navigationItem.unselectedIcon),
                                                            contentDescription = navigationItem.label,
                                                        )
                                                        Text(
                                                            text = navigationItem.label,
                                                            style = MaterialTheme.typography.labelSmall.copy(
                                                                fontSize = 10.sp,
                                                                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                                                            )
                                                        )
                                                    }
                                                },
                                                onClick = {
                                                    navController.navigate(navigationItem.route)
                                                },
                                                colors = NavigationBarItemDefaults.colors(
                                                    indicatorColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                                                        elevation = 0.dp
                                                    ),
                                                    selectedIconColor = MaterialTheme.colorScheme.primary,
                                                    selectedTextColor = MaterialTheme.colorScheme.primary,
                                                    unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                                                    unselectedTextColor = MaterialTheme.colorScheme.onSurface
                                                )
                                            )
                                        }
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    AppNavHost(
                        modifier = Modifier
                            .fillMaxSize(),
                            //.padding(innerPadding),
                        navController = navController
                    )
                }
            }
        }
    }
}