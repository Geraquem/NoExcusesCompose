package com.mmfsin.migration.models.convert

data class OldNote(
    val id: String,
    val title: String,
    val description: String,
    val date: Long,
    val pinned: Boolean
)