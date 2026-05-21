package com.example.movieapp2.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.movieapp2.data.datastore.FilterParams
import com.example.movieapp2.data.datastore.FilterSettingsStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FilterViewModel(
    private val settingsStore: FilterSettingsStore
) : ViewModel() {

    private val _filterParams = MutableStateFlow(FilterParams("Все", 0))
    val filterParams: StateFlow<FilterParams> = _filterParams.asStateFlow()

    init {
        viewModelScope.launch {
            settingsStore.settingsFlow.collect { params ->
                _filterParams.value = params
            }
        }
    }

    fun saveSettings(category: String, minScore: Int) {
        viewModelScope.launch {
            settingsStore.saveCategory(category)
            settingsStore.saveMinScore(minScore)
        }
    }
}

class FilterViewModelFactory(private val settingsStore: FilterSettingsStore) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FilterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FilterViewModel(settingsStore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}