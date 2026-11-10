package com.mmfsin.noexcuses.data.migration

import android.content.Context
import android.util.Log
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
        val routineIds = routines.mapTo(mutableSetOf()) { it.id }
        val days = oldData.days
            .filter { it.routineId in routineIds }
            .mapIndexed { index, oldDay -> oldDay.toDayDTO(index) }
        val dayIds = days.mapTo(mutableSetOf()) { it.id }
        val exercisesRtn = oldData.exercisesRtn
            .filter { it.exerciseId != null && it.dayId in dayIds }
            .mapIndexed { index, oldExercise -> oldExercise.toExerciseRtnDTO(index) }
        val exerciseRtnIds = exercisesRtn.mapTo(mutableSetOf()) { it.id }
        val series = oldData.series
            .filter { it.exerciseRtnId in exerciseRtnIds }
            .mapIndexed { index, oldSerie -> oldSerie.toSerieDTO(index) }

        val skippedDays = oldData.days.size - days.size
        val skippedExercises = oldData.exercisesRtn.size - exercisesRtn.size
        val skippedSeries = oldData.series.size - series.size
        if (skippedDays > 0 || skippedExercises > 0 || skippedSeries > 0) {
            Log.w(
                TAG,
                "Skipping legacy records with missing parents: " +
                    "$skippedDays days, $skippedExercises routine exercises, $skippedSeries series"
            )
        }

        migrationDAO.migrate(
            notes = oldData.notes.map { oldNote -> oldNote.toNoteDTO() },
            exercises = oldData.exercises.map { oldExercise -> oldExercise.toExerciseDTO() },
            routines = routines,
            days = days,
            exercisesRtn = exercisesRtn,
            series = series
        )

        RealmMigrationManager.deleteInstalledData(context)

        prefs.updateMigrationState(true)
        check(prefs.getMigrationState()) {
            "Could not persist the legacy Realm migration state"
        }
    }

    private companion object {
        const val TAG = "RealmMigrator"
    }
}
