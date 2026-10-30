package com.mmfsin.migration.models.convert

data class CompleteOldData(
    val notes: List<OldNote>,
    val routines: List<OldRoutine>,
    val days: List<OldDay>
)
