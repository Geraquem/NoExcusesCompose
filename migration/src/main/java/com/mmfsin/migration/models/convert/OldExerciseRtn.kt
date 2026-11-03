package com.mmfsin.migration.models.convert

open class OldExerciseRtn(
    var id: String,
    var routineId: String,
    var dayId: String,
    var exerciseId: String?,
    var time: Double?,
    var notes: String?,
    var position: Int,
    var superSerie: Boolean
)

open class OldSerie(
    var id: String,
    var exerciseRtnId: String,
    var reps: Int?,
    var weight: Double?,
)