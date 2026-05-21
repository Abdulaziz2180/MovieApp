package com.example.movieapp2.presentation.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterScreen(
    onBackClick: () -> Unit,
    viewModel: FilterViewModel
) {
    val filterParams by viewModel.filterParams.collectAsState()
    var selectedCategory by remember { mutableStateOf(filterParams.category) }
    var minScore by remember { mutableStateOf(filterParams.minScore) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Настройки фильтрации") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                },
                actions = {
                    Button(onClick = {
                        viewModel.saveSettings(selectedCategory, minScore)
                        onBackClick()
                    }) {
                        Text("Готово")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text("Категория", style = MaterialTheme.typography.titleMedium)
                val categories = listOf("Все", "Драма", "Криминал", "Боевик", "Фэнтези")
                var isExpanded by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = isExpanded,
                    onExpandedChange = { isExpanded = !isExpanded }
                ) {
                    TextField(
                        value = selectedCategory,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
                        modifier = Modifier.menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = isExpanded,
                        onDismissRequest = { isExpanded = false }
                    ) {
                        categories.forEach { category ->
                            DropdownMenuItem(
                                text = { Text(category) },
                                onClick = {
                                    selectedCategory = category
                                    isExpanded = false
                                }
                            )
                        }
                    }
                }
            }
            item {
                Text("Минимальный рейтинг", style = MaterialTheme.typography.titleMedium)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("0")
                    Slider(
                        value = minScore.toFloat(),
                        onValueChange = { minScore = it.toInt() },
                        valueRange = 0f..10f,
                        steps = 10,
                        modifier = Modifier.weight(1f)
                    )
                    Text("10")
                }
                Text("Текущий рейтинг: $minScore")
            }
        }
    }
}