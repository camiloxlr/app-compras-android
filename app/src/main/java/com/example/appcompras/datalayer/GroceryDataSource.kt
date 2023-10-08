package com.example.appcompras.datalayer

import androidx.compose.runtime.State
import com.example.appcompras.model.ItemData
import com.example.appcompras.model.RequestStatus
import javax.inject.Inject

class GroceryDataSource @Inject constructor(
    private val networkService: GroceryNetworkService
){
    val state: State<RequestStatus> = networkService.state

    suspend fun getItems() = networkService.getItems()

    suspend fun addItem(title: String) = networkService.addItem(title)

    suspend fun removeItem(item: ItemData) = networkService.removeItem(item)

    suspend fun updateItem(item: ItemData) = networkService.updateItem(item)


}