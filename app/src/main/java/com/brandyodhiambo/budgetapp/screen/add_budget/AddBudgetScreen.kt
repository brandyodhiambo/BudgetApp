package com.brandyodhiambo.budgetapp.screen.add_budget

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
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
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.brandyodhiambo.budgetapp.navigation.Destinations
import com.brandyodhiambo.budgetapp.ui.theme.BudgetAppTheme
import com.brandyodhiambo.budgetapp.utils.categories
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun AddBudgetScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: AddBudgetViewModel = hiltViewModel(),
) {
    var selectedCategory by rememberSaveable { mutableStateOf("") }
    var itemName by rememberSaveable { mutableStateOf("") }
    var allocationAmount by rememberSaveable { mutableStateOf("") }
    val snackHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(viewModel) {
        withContext(Dispatchers.Main.immediate) {
            viewModel.eventFlow.collect { event ->
                when (event) {
                    is AddBudgetEvent.NavigateSuccessScreen -> {
                        navController.navigate(
                            Destinations.BudgetAdded(
                                budgetItemName = event.itemName,
                                amount = event.amount,
                                date = event.date,
                            )
                        )
                    }

                    is AddBudgetEvent.SnackbarEvent -> {
                        snackHostState.showSnackbar(
                            message = event.message,
                        )
                    }
                }
            }
        }
    }

    AddBudgetScreenContent(
        modifier = modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(
                hostState = snackHostState,
            )
        },
        selectedCategory = selectedCategory,
        onSelectCategory = {
            selectedCategory = it
        },
        itemName = itemName,
        onItemNameChange = {
            itemName = it
        },
        allocationAmount = allocationAmount,
        onAllocationAmountChange = {
            allocationAmount = it
        },
        onClickCreateNewBudgetItem = {
            viewModel.addTransaction(
                selectedCategory = selectedCategory,
                itemName = itemName,
                allocationAmount = allocationAmount
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddBudgetScreenContent(
    selectedCategory: String,
    onSelectCategory: (String) -> Unit,
    itemName: String,
    onItemNameChange: (String) -> Unit,
    allocationAmount: String,
    onAllocationAmountChange: (String) -> Unit,
    onClickCreateNewBudgetItem: () -> Unit,
    snackbarHost: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        modifier = modifier,
        snackbarHost = snackbarHost,
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Add New Budget Item",
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                FormField(
                    title = "Category",
                ) {
                    Dropdown(
                        options = categories.map { it.name },
                        selectedOption = selectedCategory,
                        onOptionSelected = onSelectCategory,
                        isError = false,
                        errorText = "",
                        placeholder = {
                            Text(
                                text = "Select category",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.onSurface.copy(
                                        alpha = 0.5f
                                    )
                                ),
                            )
                        }
                    )
                }
            }
            item {
                FormField(
                    title = "Item Name",
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .height(48.dp)
                            .fillMaxWidth(),
                        value = itemName,
                        onValueChange = onItemNameChange,
                        shape = MaterialTheme.shapes.small,
                        placeholder = {
                            Text(
                                text = "Enter item name",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.onSurface.copy(
                                        alpha = 0.5f
                                    )
                                ),
                            )
                        },
                        textStyle = MaterialTheme.typography.bodySmall,
                    )
                }
            }

            item {
                FormField(
                    title = "Allocation",
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .height(48.dp)
                            .fillMaxWidth(),
                        value = allocationAmount,
                        onValueChange = onAllocationAmountChange,
                        shape = MaterialTheme.shapes.small,
                        textStyle = MaterialTheme.typography.bodySmall,
                        leadingIcon = {
                            Row(
                                modifier = Modifier.padding(start = 16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    text = "KSh",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontSize = 10.sp,
                                    ),
                                )

                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Dropdown",
                                    tint = MaterialTheme.colorScheme.onSurface.copy(
                                        alpha = 0.6f,
                                    )
                                )
                            }
                        },
                        placeholder = {
                            Text(
                                text = "Enter allocation amount",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.onSurface.copy(
                                        alpha = 0.5f
                                    )
                                ),
                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number,
                        ),
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Button(
                    modifier = Modifier
                        .height(48.dp)
                        .fillMaxWidth(),
                    shape = MaterialTheme.shapes.small,
                    onClick = {
                        keyboardController?.hide()
                        onClickCreateNewBudgetItem()
                    },
                ) {
                    Text(
                        text = "Create New Budget Item",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                        ),
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> Dropdown(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
    shape: CornerBasedShape = MaterialTheme.shapes.small,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    textStyle: TextStyle = MaterialTheme.typography.bodySmall,
    colors: TextFieldColors = TextFieldDefaults.colors(
        unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground,
        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
    ),
    onOptionSelected: (T) -> Unit,
    options: List<T>,
    selectedOption: T,
    isError: Boolean = false,
    enabled: Boolean = true,
    errorText: String = "",
) {
    var expanded by remember { mutableStateOf(false) }
    Column {
        ExposedDropdownMenuBox(
            modifier = modifier
                .fillMaxWidth()
                .height(48.dp),
            expanded = expanded,
            onExpandedChange = {
                if (enabled) {
                    expanded = !expanded
                }
            },
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                readOnly = true,
                value = selectedOption.toString(),
                onValueChange = {},
                leadingIcon = leadingIcon,
                shape = shape,
                textStyle = textStyle,
                placeholder = {
                    if (selectedOption.toString().isEmpty()) {
                        placeholder?.invoke()
                    }
                },
                trailingIcon = {
                    if (enabled) {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    }
                },
                colors = colors,
                interactionSource = remember { MutableInteractionSource() }
                    .also { interactionSource ->
                        LaunchedEffect(interactionSource) {
                            interactionSource.interactions.collect {
                                if (it is PressInteraction.Release) {
                                    onClick()
                                }
                            }
                        }
                    },
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = selectionOption.toString(),
                                style = MaterialTheme.typography.labelMedium,
                            )
                        },
                        onClick = {
                            onOptionSelected(selectionOption)
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
        if (isError) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = errorText,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.End,
            )
        }
    }
}

@Composable
fun FormField(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
        )
        content()
    }
}

@Preview
@Composable
fun AddBudgetScreenPreview(modifier: Modifier = Modifier) {
    BudgetAppTheme {
        AddBudgetScreenContent(
            selectedCategory = "Shopping",
            allocationAmount = "200,000",
            itemName = "New Cloth",
            onAllocationAmountChange = {},
            onItemNameChange = {},
            onSelectCategory = {},
            onClickCreateNewBudgetItem = {},
            snackbarHost = {},
        )
    }

}