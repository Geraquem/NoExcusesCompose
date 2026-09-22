package com.mmfsin.noexcusescompose.di

import android.content.Context
import androidx.room.Room
import com.mmfsin.noexcusescompose.data.ddbb.RoomConfiguration
import com.mmfsin.noexcusescompose.data.ddbb.daos.ExercisesDAO
import com.mmfsin.noexcusescompose.data.ddbb.daos.MuscularGroupsDAO
import com.mmfsin.noexcusescompose.util.DDBB_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): RoomConfiguration =
        Room.databaseBuilder(
            context,
            RoomConfiguration::class.java,
            DDBB_NAME
        )
            .fallbackToDestructiveMigration(true)
            .build()

    @Provides
    fun provideMuscularGroupsDAO(db: RoomConfiguration): MuscularGroupsDAO = db.muscularGroupsDAO()

    @Provides
    fun provideExercisesDAO(db: RoomConfiguration): ExercisesDAO = db.exercisesDAO()
}