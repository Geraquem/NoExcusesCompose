package com.mmfsin.noexcuses.di

import com.mmfsin.noexcuses.data.repository.ExercisesRepository
import com.mmfsin.noexcuses.data.repository.MenuRepository
import com.mmfsin.noexcuses.data.repository.NotesRepository
import com.mmfsin.noexcuses.data.repository.RmsRepository
import com.mmfsin.noexcuses.data.repository.RoutinesRepository
import com.mmfsin.noexcuses.data.repository.StretchRepository
import com.mmfsin.noexcuses.domain.interfaces.IExercisesRepository
import com.mmfsin.noexcuses.domain.interfaces.IMenuRepository
import com.mmfsin.noexcuses.domain.interfaces.INotesRepository
import com.mmfsin.noexcuses.domain.interfaces.IRmsRepository
import com.mmfsin.noexcuses.domain.interfaces.IRoutinesRepository
import com.mmfsin.noexcuses.domain.interfaces.IStretchRepository
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

    @Binds
    fun bindRoutinesRepository(repository: RoutinesRepository): IRoutinesRepository

    @Binds
    fun bindStretchRepository(repository: StretchRepository): IStretchRepository

    @Binds
    fun bindNotesRepository(repository: NotesRepository): INotesRepository

    @Binds
    fun bindRmsRepository(repository: RmsRepository): IRmsRepository
}