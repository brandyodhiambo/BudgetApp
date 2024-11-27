package com.brandyodhiambo.budgetapp.screen.add_budget

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.brandyodhiambo.budgetapp.R
import com.brandyodhiambo.budgetapp.navigation.Destinations
import com.brandyodhiambo.budgetapp.ui.theme.BudgetAppTheme

@Composable
fun BudgetAddedScreen(
    budgetItemName: String,
    amount: String,
    date: String,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    BudgetAddedScreenContent(
        modifier = modifier.fillMaxSize(),
        budgetItemName = budgetItemName,
        amount = amount,
        date = date,
        onClickGoBackHome = {
            navController.navigate(Destinations.Home)
        },
        onClickRateExperience = {
            Toast.makeText(context, "Thanks For Rating us", Toast.LENGTH_SHORT).show()
        }
    )
}

@Composable
fun BudgetAddedScreenContent(
    budgetItemName: String,
    amount: String,
    date: String,
    onClickGoBackHome: () -> Unit,
    onClickRateExperience: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 16.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 64.dp
                    ),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Button(
                    modifier = Modifier
                        .height(48.dp)
                        .fillMaxWidth(),
                    shape = MaterialTheme.shapes.small,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    ),
                    onClick = onClickGoBackHome,
                ) {
                    Text(
                        text = "Go back to Home",
                        style = MaterialTheme.typography.labelSmall,
                    )
                }

                OutlinedButton(
                    modifier = Modifier
                        .height(48.dp)
                        .fillMaxWidth(),
                    shape = MaterialTheme.shapes.small,
                    onClick = onClickRateExperience,
                ) {
                    Text(
                        text = "Rate your Experience",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.secondary,
                        ),
                    )
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.ic_confetti),
                    contentDescription = "Confetti",
                )
            }

            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Budget Item Added",
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.secondary,
                        ),
                    )
                    Text(
                        text = "Budget item has been added successfully,",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurface.copy(
                                alpha = 0.5f
                            )
                        ),
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                HorizontalDivider()
            }

            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    AddedItemRow(
                        title = "Budget Item Name",
                        value = budgetItemName,
                        titleStyle = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.onSurface.copy(
                                alpha = 0.5f
                            )
                        ),
                    )

                    AddedItemRow(
                        title = "Amount",
                        value = "Ksh $amount",
                        valueStyle = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    )

                    AddedItemRow(
                        title = "Date",
                        value = date,
                        titleStyle = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.onSurface.copy(
                                alpha = 0.5f
                            )
                        ),
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(56.dp))
            }
        }
    }
}


@Composable
fun AddedItemRow(
    title: String,
    value: String,
    titleStyle: TextStyle = MaterialTheme.typography.bodySmall,
    valueStyle: TextStyle = MaterialTheme.typography.bodySmall,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = title,
            style = titleStyle,
        )

        Text(
            text = value,
            style = valueStyle,
        )
    }
}

@Preview
@Composable
private fun BudgetAddedScreenPreview() {
    BudgetAppTheme {
        BudgetAddedScreenContent(
            budgetItemName = "Shopping",
            amount = "2000",
            date = "12th August 2021",
            onClickGoBackHome = {},
            onClickRateExperience = {},
        )
    }
}