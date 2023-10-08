package com.example.appcompras.datalayer

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.appcompras.model.ItemData
import com.example.appcompras.model.RequestStatus
import kotlinx.coroutines.delay

class GroceryNetworkService {
    private val itemsList: MutableList<ItemData> = mutableListOf()

    private val _state = mutableStateOf(RequestStatus(false, itemsList))

    val state: State<RequestStatus> = _state

    suspend fun getItems() {
        _state.value = _state.value.copy(isLoading = true)
        delay(500)
        itemsList.addAll(
            mutableListOf(
                ItemData("Maçã", false, 0),
                ItemData("Banana", false, 1),
            )
        )
        _state.value = _state.value.copy(isLoading = false, data = itemsList)
    }

    suspend fun addItem(title: String) {
        _state.value = _state.value.copy(isLoading = true)
        delay(500)
        itemsList.add(ItemData(title, false, itemsList.size))
        _state.value = _state.value.copy(isLoading = false, data = itemsList)

    }

    suspend fun updateItem(item: ItemData) {
        _state.value = _state.value.copy(isLoading = true)
        delay(500)

        val index = itemsList.indexOf(item)
        if (index != -1) {
            val updatedItems = itemsList.toMutableList()
            updatedItems[index].checked = !updatedItems[index].checked
            itemsList.clear()
            itemsList.addAll(updatedItems)
            _state.value = _state.value.copy(isLoading = false, data = itemsList)
        }
    }

    suspend fun removeItem(item: ItemData) {
        _state.value = _state.value.copy(isLoading = true)
        delay(500)
        itemsList.remove(item)
        _state.value = _state.value.copy(isLoading = false, data = itemsList)
    }
}