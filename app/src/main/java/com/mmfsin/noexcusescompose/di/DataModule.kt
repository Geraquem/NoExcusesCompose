package com.mmfsin.noexcusescompose.di

import com.mmfsin.noexcusescompose.data.repository.MenuRepository
import com.mmfsin.noexcusescompose.domain.interfaces.IMenuRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindMenuRepository(repository: MenuRepository): IMenuRepository
}