package com.mmfsin.migration
//
//import android.content.Context
//import com.mmfsin.migration.models.CalendarInfoDTO
//import com.mmfsin.migration.models.ChExerciseDTO
//import com.mmfsin.migration.models.DataDTO
//import com.mmfsin.migration.models.DayDTO
//import com.mmfsin.migration.models.DefaultDayDTO
//import com.mmfsin.migration.models.DefaultExerciseDTO
//import com.mmfsin.migration.models.DefaultRoutineDTO
//import com.mmfsin.migration.models.ExerciseDTO
//import com.mmfsin.migration.models.MaximumDataDTO
//import com.mmfsin.migration.models.MuscularGroupDTO
//import com.mmfsin.migration.models.MyRoutineDTO
//import com.mmfsin.migration.models.NoteDTO
//import com.mmfsin.migration.models.StretchingDTO
//import io.realm.kotlin.Realm
//import io.realm.kotlin.RealmConfiguration
//import java.io.File
//
//data class RealmInspectionResult(
//    val calendarInfo: Int,
//    val chExercises: Int,
//    val data: Int,
//    val days: Int,
//    val defaultRoutines: Int,
//    val defaultDays: Int,
//    val defaultExercises: Int,
//    val exercises: Int,
//    val maximumData: Int,
//    val muscularGroups: Int,
//    val myRoutines: Int,
//    val notes: Int,
//    val stretchings: Int
//)
//
//object RealmMigrationManager {
//
//    fun inspectBackup(context: Context): RealmInspectionResult {
//
//        // 1. Copiar el Realm de assets a una carpeta aislada.
//        val directory = File(context.cacheDir, "realm-migration-test")
//        directory.mkdirs()
//
//        val realmFile = File(directory, "default.realm")
//
//        if (!realmFile.exists()) {
//            context.assets.open("default.realm").use { input ->
//                realmFile.outputStream().use { output ->
//                    input.copyTo(output)
//                }
//            }
//        }
//
//        // 2. Configurar Realm con el esquema original.
//        val config = RealmConfiguration.Builder(
//            schema = setOf(
//                CalendarInfoDTO::class,
//                ChExerciseDTO::class,
//                DataDTO::class,
//                DayDTO::class,
//                DefaultRoutineDTO::class,
//                DefaultDayDTO::class,
//                DefaultExerciseDTO::class,
//                ExerciseDTO::class,
//                MaximumDataDTO::class,
//                MuscularGroupDTO::class,
//                MyRoutineDTO::class,
//                NoteDTO::class,
//                StretchingDTO::class
//            )
//        ).directory(directory.absolutePath)
//            .name("default.realm")
//            .schemaVersion(3)
//            .build()
//
//        // 3. Abrir la copia, nunca la base de datos original.
//        val realm = Realm.open(config)
//
//        return try {
//            RealmInspectionResult(
//                calendarInfo = realm.query(CalendarInfoDTO::class).find().size,
//                chExercises = realm.query(ChExerciseDTO::class).find().size,
//                data = realm.query(DataDTO::class).find().size,
//                days = realm.query(DayDTO::class).find().size,
//                defaultRoutines = realm.query(DefaultRoutineDTO::class).find().size,
//                defaultDays = realm.query(DefaultDayDTO::class).find().size,
//                defaultExercises = realm.query(DefaultExerciseDTO::class).find().size,
//                exercises = realm.query(ExerciseDTO::class).find().size,
//                maximumData = realm.query(MaximumDataDTO::class).find().size,
//                muscularGroups = realm.query(MuscularGroupDTO::class).find().size,
//                myRoutines = realm.query(MyRoutineDTO::class).find().size,
//                notes = realm.query(NoteDTO::class).find().size,
//                stretchings = realm.query(StretchingDTO::class).find().size
//            )
//        } finally {
//            realm.close()
//        }
//    }
//}
