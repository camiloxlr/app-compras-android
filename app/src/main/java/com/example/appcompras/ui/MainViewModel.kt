package com.example.appcompras.ui

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appcompras.datalayer.GroceryRepository
import com.example.appcompras.model.ItemData
import com.example.appcompras.model.RequestStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: GroceryRepository,

):ViewModel() {
    val state: State<RequestStatus> = repository.state

    init {
        viewModelScope.launch {
            repository.getItems()
        }
    }

    fun addItem(title: String) {
        viewModelScope.launch {
            repository.addItem(title)
        }
    }

    fun removeItem(item: ItemData) {
        viewModelScope.launch {
            repository.removeItem(item)
        }
    }

    fun updateItem(item: ItemData) {
        viewModelScope.launch {
            repository.updateItem(item)
        }
    }
}