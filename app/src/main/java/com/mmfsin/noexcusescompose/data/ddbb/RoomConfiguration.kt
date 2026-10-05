package com.mmfsin.noexcusescompose.data.ddbb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mmfsin.noexcusescompose.data.ddbb.daos.ExercisesDAO
import com.mmfsin.noexcusescompose.data.ddbb.daos.MuscularGroupsDAO
import com.mmfsin.noexcusescompose.data.ddbb.daos.NotesDAO
import com.mmfsin.noexcusescompose.data.ddbb.daos.RoutinesDAO
import com.mmfsin.noexcusescompose.data.ddbb.daos.StretchDAO
import com.mmfsin.noexcusescompose.data.models.DayDTO
import com.mmfsin.noexcusescompose.data.models.ExerciseDTO
import com.mmfsin.noexcusescompose.data.models.ExerciseRtnDTO
import com.mmfsin.noexcusescompose.data.models.MuscularGroupDTO
import com.mmfsin.noexcusescompose.data.models.MyRoutineDTO
import com.mmfsin.noexcusescompose.data.models.NoteDTO
import com.mmfsin.noexcusescompose.data.models.SerieDTO
import com.mmfsin.noexcusescompose.data.models.StretchDTO

@Database(
    entities = [
        MuscularGroupDTO::class,
        ExerciseDTO::class,
        MyRoutineDTO::class,
        DayDTO::class,
        ExerciseRtnDTO::class,
        SerieDTO::class,
        StretchDTO::class,
        NoteDTO::class,
    ],
    version = 1
)
abstract class RoomConfiguration : RoomDatabase() {
    abstract fun muscularGroupsDAO(): MuscularGroupsDAO
    abstract fun exercisesDAO(): ExercisesDAO
    abstract fun routinesDAO(): RoutinesDAO
    abstract fun stretchDAO(): StretchDAO
    abstract fun notesDAO(): NotesDAO
}