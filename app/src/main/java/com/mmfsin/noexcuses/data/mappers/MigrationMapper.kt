package com.mmfsin.noexcuses.data.mappers

import com.mmfsin.migration.models.convert.OldDay
import com.mmfsin.migration.models.convert.OldNote
import com.mmfsin.migration.models.convert.OldRoutine
import com.mmfsin.noexcuses.data.models.DayDTO
import com.mmfsin.noexcuses.data.models.MyRoutineDTO
import com.mmfsin.noexcuses.data.models.NoteDTO

fun OldNote.toNoteDTO() = NoteDTO(
    id = id,
    title = title,
    description = description,
    date = date,
    pinned = pinned
)

fun OldRoutine.toRoutineDTO(i: Int) = MyRoutineDTO(
    id = id,
    name = title,
    description = description,
    pinned = doingIt,
    createdByUser = createdByUser,
    pinnedDate = pinnedDate,
    order = i
)

fun OldDay.toDayDTO(i: Int) = DayDTO(
    id = id,
    routineId = routineId,
    name = title,
    order = i
)