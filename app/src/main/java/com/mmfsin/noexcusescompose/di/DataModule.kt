package com.mmfsin.noexcusescompose.di

import com.mmfsin.noexcusescompose.data.repository.ExercisesRepository
import com.mmfsin.noexcusescompose.data.repository.MenuRepository
import com.mmfsin.noexcusescompose.domain.interfaces.IExercisesRepository
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

    @Binds
    fun bindExercisesRepository(repository: ExercisesRepository): IExercisesRepository
}