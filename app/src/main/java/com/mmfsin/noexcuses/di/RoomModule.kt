package com.mmfsin.noexcuses.di

import android.content.Context
import androidx.room.Room
import com.mmfsin.noexcuses.data.ddbb.RoomConfiguration
import com.mmfsin.noexcuses.data.ddbb.daos.ExercisesDAO
import com.mmfsin.noexcuses.data.ddbb.daos.MenuDAO
import com.mmfsin.noexcuses.data.ddbb.daos.MigrationDAO
import com.mmfsin.noexcuses.data.ddbb.daos.MuscularGroupsDAO
import com.mmfsin.noexcuses.data.ddbb.daos.NotesDAO
import com.mmfsin.noexcuses.data.ddbb.daos.RmsDAO
import com.mmfsin.noexcuses.data.ddbb.daos.RoutinesDAO
import com.mmfsin.noexcuses.data.ddbb.daos.StretchDAO
import com.mmfsin.noexcuses.util.DDBB_NAME
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
        ).fallbackToDestructiveMigration(true)
            .build()

    @Provides
    fun provideMigrationDAO(db: RoomConfiguration): MigrationDAO = db.migrationDAO()

    @Provides
    fun provideMenuDAO(db: RoomConfiguration): MenuDAO = db.menuDAO()

    @Provides
    fun provideMuscularGroupsDAO(db: RoomConfiguration): MuscularGroupsDAO = db.muscularGroupsDAO()

    @Provides
    fun provideExercisesDAO(db: RoomConfiguration): ExercisesDAO = db.exercisesDAO()

    @Provides
    fun provideRoutinesDAO(db: RoomConfiguration): RoutinesDAO = db.routinesDAO()

    @Provides
    fun provideStretchDAO(db: RoomConfiguration): StretchDAO = db.stretchDAO()

    @Provides
    fun provideNotesDAO(db: RoomConfiguration): NotesDAO = db.notesDAO()

    @Provides
    fun provideRmsDAO(db: RoomConfiguration): RmsDAO = db.rmsDAO()
}