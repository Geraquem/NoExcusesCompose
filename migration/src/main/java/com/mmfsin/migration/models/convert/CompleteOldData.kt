package com.mmfsin.migration.models.convert

data class CompleteOldData(
    val notes: List<OldNote> = emptyList(),
    val exercises: List<OldExercise> = emptyList(),
    val routines: List<OldRoutine> = emptyList(),
    val days: List<OldDay> = emptyList()
)
