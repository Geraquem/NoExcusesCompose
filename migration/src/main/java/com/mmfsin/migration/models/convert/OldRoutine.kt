package com.mmfsin.migration.models.convert

data class OldRoutine(
    val id: String,
    val title: String,
    val description: String?,
    val doingIt: Boolean,
    val createdByUser: Boolean,
    val pinnedDate: Long?
)
