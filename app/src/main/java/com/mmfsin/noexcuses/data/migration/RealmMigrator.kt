package com.mmfsin.noexcuses.data.migration

import android.content.Context
import com.mmfsin.migration.RealmMigrationManager
import com.mmfsin.migration.models.convert.OldDay
import com.mmfsin.migration.models.convert.OldRoutine
import com.mmfsin.noexcuses.data.ddbb.SharedPrefs
import com.mmfsin.noexcuses.data.ddbb.daos.MigrationDAO
import com.mmfsin.noexcuses.data.mappers.toNoteDTO
import com.mmfsin.noexcuses.data.mappers.toRoutineDTO
import com.mmfsin.noexcuses.data.models.DayDTO
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

        if (oldData.notes.isNotEmpty()) {
            migrationDAO.insertNotes(oldData.notes.map { oldNote -> oldNote.toNoteDTO() })
        }

        val routines = oldData.routines
            .sortedWith(compareBy<OldRoutine> { it.title.lowercase() }.thenBy { it.id })
            .mapIndexed { index, oldRoutine -> oldRoutine.toRoutineDTO(index) }

        val routineIds = routines.mapTo(mutableSetOf()) { it.id }
        val days = oldData.days
            .filter { it.routineId in routineIds }
            .groupBy { it.routineId }
            .flatMap { (routineId, routineDays) ->
                routineDays.sortedWith(compareBy<OldDay> { it.title.lowercase() }.thenBy { it.id })
                    .mapIndexed { index, oldDay ->
                        DayDTO(
                            id = oldDay.id,
                            routineId = routineId,
                            name = oldDay.title,
                            order = index
                        )
                    }
            }

        migrationDAO.migrate(
            notes = oldData.notes.map { oldNote -> oldNote.toNoteDTO() },
            routines = routines,
            days = days
        )

        prefs.updateMigrationState(true)
        check(prefs.getMigrationState()) {
            "Could not persist the legacy Realm migration state"
        }
    }
}
