package com.mmfsin.migration.models.convert

data class CompleteOldData(
    val notes: List<OldNote>,
    val exercises: List<OldExercise>,
    val routines: List<OldRoutine>,
    val days: List<OldDay>,
    val exercisesRtn: List<OldExerciseRtn>,
    val series: List<OldSerie>,
)
