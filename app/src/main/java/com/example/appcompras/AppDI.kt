package com.example.appcompras

import com.example.appcompras.datalayer.GroceryDataSource
import com.example.appcompras.datalayer.GroceryNetworkService
import com.example.appcompras.datalayer.GroceryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppDI {

    @Provides
    @Singleton
    fun provideGroceryNetworkService() = GroceryNetworkService()


    @Provides
    @Singleton
    fun provideGroceryDataSource(groceryNetworkService: GroceryNetworkService) =
        GroceryDataSource(groceryNetworkService)

    @Provides
    @Singleton
    fun provideGroceryRepository(groceryDataSource: GroceryDataSource) =
        GroceryRepository(groceryDataSource)
}