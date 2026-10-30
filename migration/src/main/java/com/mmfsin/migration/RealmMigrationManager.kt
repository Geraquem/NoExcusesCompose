package com.mmfsin.migration

import android.content.Context
import com.mmfsin.migration.mappers.toOldNote
import com.mmfsin.migration.models.convert.OldNote
import com.mmfsin.migration.models.realm.NoteDTO
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import java.io.File

object RealmMigrationManager {
    fun readInstalledNotes(context: Context): List<OldNote> {
        val realmFile = File(context.filesDir, "default.realm")
        if (!realmFile.isFile) return emptyList()

        val directory = File(context.cacheDir, "legacy-realm-read")
        if (!directory.exists() && !directory.mkdirs()) {
            error("Could not create a temporary directory for the legacy Realm")
        }
        val copiedRealmFile = File(directory, realmFile.name)
        realmFile.copyTo(copiedRealmFile, overwrite = true)

        val config = RealmConfiguration.Builder(
            schema = setOf(
                NoteDTO::class
            )
        ).directory(directory.absolutePath)
            .name(copiedRealmFile.name)
            .schemaVersion(3)
            .build()

        val realm = Realm.open(config)

        return try {
            realm.query(NoteDTO::class).find().map { noteDTO -> noteDTO.toOldNote() }
        } finally {
            realm.close()
        }
    }
}
