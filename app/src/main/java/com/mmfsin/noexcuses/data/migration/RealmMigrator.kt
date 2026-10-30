package com.mmfsin.noexcuses.data.migration

import android.content.Context
import com.mmfsin.migration.RealmMigrationManager
import com.mmfsin.noexcuses.data.ddbb.SharedPrefs
import com.mmfsin.noexcuses.data.ddbb.daos.NotesDAO
import com.mmfsin.noexcuses.data.mappers.toNoteDTO
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class RealmMigrator @Inject constructor(
    @ApplicationContext private val context: Context,
    private val prefs: SharedPrefs,
    private val notesDAO: NotesDAO
) {
    suspend fun migrateIfNeeded() = withContext(Dispatchers.IO) {
        if (prefs.getMigrationState()) {
            return@withContext
        }

        val oldNotes = RealmMigrationManager.readInstalledNotes(context)
        if (oldNotes.isNotEmpty()) {
            notesDAO.insertNotes(oldNotes.map { oldNote -> oldNote.toNoteDTO() })
        }

        prefs.updateMigrationState(true)
        check(prefs.getMigrationState()) {
            "Could not persist the legacy Realm migration state"
        }
    }
}
