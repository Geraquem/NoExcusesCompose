package com.mmfsin.noexcuses.data.ddbb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mmfsin.noexcuses.data.ddbb.daos.ExercisesDAO
import com.mmfsin.noexcuses.data.ddbb.daos.MenuDAO
import com.mmfsin.noexcuses.data.ddbb.daos.MigrationDAO
import com.mmfsin.noexcuses.data.ddbb.daos.MuscularGroupsDAO
import com.mmfsin.noexcuses.data.ddbb.daos.NotesDAO
import com.mmfsin.noexcuses.data.ddbb.daos.RmsDAO
import com.mmfsin.noexcuses.data.ddbb.daos.RoutinesDAO
import com.mmfsin.noexcuses.data.ddbb.daos.StretchDAO
import com.mmfsin.noexcuses.data.models.DayDTO
import com.mmfsin.noexcuses.data.models.ExerciseDTO
import com.mmfsin.noexcuses.data.models.ExerciseRtnDTO
import com.mmfsin.noexcuses.data.models.MenuImagesDTO
import com.mmfsin.noexcuses.data.models.MuscularGroupDTO
import com.mmfsin.noexcuses.data.models.MyRoutineDTO
import com.mmfsin.noexcuses.data.models.NoteDTO
import com.mmfsin.noexcuses.data.models.RmDTO
import com.mmfsin.noexcuses.data.models.SerieDTO
import com.mmfsin.noexcuses.data.models.StretchDTO

@Database(
    entities = [
        MenuImagesDTO::class,
        MuscularGroupDTO::class,
        ExerciseDTO::class,
        MyRoutineDTO::class,
        DayDTO::class,
        ExerciseRtnDTO::class,
        SerieDTO::class,
        StretchDTO::class,
        NoteDTO::class,
        RmDTO::class,
    ],
    version = 1
)
abstract class RoomConfiguration : RoomDatabase() {
    abstract fun migrationDAO(): MigrationDAO
    abstract fun menuDAO(): MenuDAO
    abstract fun muscularGroupsDAO(): MuscularGroupsDAO
    abstract fun exercisesDAO(): ExercisesDAO
    abstract fun routinesDAO(): RoutinesDAO
    abstract fun stretchDAO(): StretchDAO
    abstract fun notesDAO(): NotesDAO
    abstract fun rmsDAO(): RmsDAO
}