package com.mmfsin.noexcusescompose.data.mappers

import com.mmfsin.noexcusescompose.data.models.NoteDTO
import java.util.UUID

fun createNoteDTO(
    title: String,
    description: String
) = NoteDTO(
    id = UUID.randomUUID().toString(),
    title = title,
    description = description,
    date = System.currentTimeMillis(),
    pinned = false,
)