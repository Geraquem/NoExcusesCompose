package com.mmfsin.noexcuses.data.mappers

import com.mmfsin.migration.models.convert.OldNote
import com.mmfsin.noexcuses.data.models.NoteDTO

fun OldNote.toNoteDTO() = NoteDTO(
    id = id,
    title = title,
    description = description,
    date = date,
    pinned = false
)