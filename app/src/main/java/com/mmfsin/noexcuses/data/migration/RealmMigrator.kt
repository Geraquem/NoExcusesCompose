package com.mmfsin.noexcuses.data.migration

import android.content.Context
import com.mmfsin.migration.RealmMigrationManager
import com.mmfsin.migration.models.convert.OldRoutine
import com.mmfsin.noexcuses.data.ddbb.SharedPrefs
import com.mmfsin.noexcuses.data.ddbb.daos.MigrationDAO
import com.mmfsin.noexcuses.data.mappers.toDayDTO
import com.mmfsin.noexcuses.data.mappers.toExerciseDTO
import com.mmfsin.noexcuses.data.mappers.toExerciseRtnDTO
import com.mmfsin.noexcuses.data.mappers.toNoteDTO
import com.mmfsin.noexcuses.data.mappers.toRoutineDTO
import com.mmfsin.noexcuses.data.mappers.toSerieDTO
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class RealmMigrator @Inject constructor(
    @ApplicationContext private val context: Context,
    private val prefs: SharedPrefs,
    private val migrationDAO: MigrationDAO
) {
    suspend fun migrateIfNeeded() = withContext(Dispatchers.IO) {
        if (prefs.getMigrationState()) {
            return@withContext
        }

        val oldData = RealmMigrationManager.readInstalledData(context)

        /**** ROUTINES ****/
        val routines = oldData.routines
            .sortedWith(compareBy<OldRoutine> { it.title.lowercase() }.thenBy { it.id })
            .mapIndexed { index, oldRoutine -> oldRoutine.toRoutineDTO(index) }

        migrationDAO.migrate(
            notes = oldData.notes.map { oldNote -> oldNote.toNoteDTO() },
            exercises = oldData.exercises.map { oldExercise -> oldExercise.toExerciseDTO() },
            routines = routines,
            days = oldData.days.mapIndexed { index, oldDay -> oldDay.toDayDTO(index) },
            exercisesRtn = oldData.exercisesRtn.mapIndexed { index, oldExRtn-> oldExRtn.toExerciseRtnDTO(index) },
            series = oldData.series.mapIndexed { index, oldSerie ->oldSerie.toSerieDTO(index)  }
        )

        prefs.updateMigrationState(true)
        check(prefs.getMigrationState()) {
            "Could not persist the legacy Realm migration state"
        }
    }
}
