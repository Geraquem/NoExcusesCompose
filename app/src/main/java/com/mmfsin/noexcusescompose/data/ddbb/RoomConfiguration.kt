package com.mmfsin.noexcusescompose.data.ddbb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mmfsin.noexcusescompose.data.ddbb.daos.ExercisesDAO
import com.mmfsin.noexcusescompose.data.ddbb.daos.MuscularGroupsDAO
import com.mmfsin.noexcusescompose.data.models.ExerciseDTO
import com.mmfsin.noexcusescompose.data.models.MuscularGroupDTO

@Database(entities = [MuscularGroupDTO::class, ExerciseDTO::class], version = 1)
abstract class RoomConfiguration : RoomDatabase() {
    abstract fun muscularGroupsDAO(): MuscularGroupsDAO
    abstract fun exercisesDAO(): ExercisesDAO
}