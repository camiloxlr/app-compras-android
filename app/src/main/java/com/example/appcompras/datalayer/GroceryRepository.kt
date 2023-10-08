package com.example.appcompras.datalayer

import androidx.compose.runtime.State
import com.example.appcompras.model.ItemData
import com.example.appcompras.model.RequestStatus
import javax.inject.Inject

class GroceryRepository  @Inject constructor(
    private val dataSource: GroceryDataSource
){
    val state: State<RequestStatus> = dataSource.state

    suspend fun getItems() = dataSource.getItems()

    suspend fun addItem(title: String) = dataSource.addItem(title)

    suspend fun removeItem(item: ItemData) = dataSource.removeItem(item)

    suspend fun updateItem(item: ItemData) = dataSource.updateItem(item)


}