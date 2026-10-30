package com.mmfsin.migration.mappers

import com.mmfsin.migration.models.convert.OldNote
import com.mmfsin.migration.models.realm.NoteDTO

fun NoteDTO.toOldNote() = OldNote(
    id = id,
    title = title,
    description = description,
    date = date,
    pinned = pinned
)