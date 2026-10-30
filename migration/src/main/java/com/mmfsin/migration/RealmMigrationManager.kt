package com.mmfsin.migration

import android.content.Context
import com.mmfsin.migration.mappers.toOldDay
import com.mmfsin.migration.mappers.toOldNote
import com.mmfsin.migration.mappers.toOldRoutine
import com.mmfsin.migration.models.convert.CompleteOldData
import com.mmfsin.migration.models.realm.DayDTO
import com.mmfsin.migration.models.realm.MyRoutineDTO
import com.mmfsin.migration.models.realm.NoteDTO
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import java.io.File

object RealmMigrationManager {
    fun readInstalledData(context: Context): CompleteOldData {
        val realmFile = File(context.filesDir, "default.realm")
        if (!realmFile.isFile) {
            return CompleteOldData(
                notes = emptyList(),
                routines = emptyList(),
                days = emptyList()
            )
        }

        val directory = File(context.cacheDir, "legacy-realm-read")
        if (!directory.exists() && !directory.mkdirs()) {
            error("Could not create a temporary directory for the legacy Realm")
        }
        val copiedRealmFile = File(directory, realmFile.name)
        realmFile.copyTo(copiedRealmFile, overwrite = true)

        val config = RealmConfiguration.Builder(
            schema = setOf(
                NoteDTO::class,
                MyRoutineDTO::class,
                DayDTO::class
            )
        ).directory(directory.absolutePath)
            .name(copiedRealmFile.name)
            .schemaVersion(3)
            .build()

        val realm = Realm.open(config)

        return try {
            CompleteOldData(
                notes = realm.query(NoteDTO::class).find().map { it.toOldNote() },
                routines = realm.query(MyRoutineDTO::class).find().map { it.toOldRoutine() },
                days = realm.query(DayDTO::class).find().map { it.toOldDay() }
            )
        } finally {
            realm.close()
        }
    }
}
