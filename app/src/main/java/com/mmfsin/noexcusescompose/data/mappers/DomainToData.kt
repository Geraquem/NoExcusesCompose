package com.mmfsin.noexcusescompose.data.mappers

import com.mmfsin.noexcusescompose.data.models.NoteDTO
import java.util.UUID

fun createNoteDTO(
    noteId:String?,
    title: String,
    description: String
) = NoteDTO(
    id = noteId ?: UUID.randomUUID().toString(),
    title = title,
    description = description,
    date = System.currentTimeMillis(),
    pinned = false,
)