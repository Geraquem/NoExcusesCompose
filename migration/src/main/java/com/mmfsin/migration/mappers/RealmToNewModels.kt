package com.mmfsin.migration.mappers

import com.mmfsin.migration.models.convert.OldDay
import com.mmfsin.migration.models.convert.OldNote
import com.mmfsin.migration.models.convert.OldRoutine
import com.mmfsin.migration.models.realm.DayDTO
import com.mmfsin.migration.models.realm.MyRoutineDTO
import com.mmfsin.migration.models.realm.NoteDTO

fun NoteDTO.toOldNote() = OldNote(
    id = id,
    title = title,
    description = description,
    date = date,
    pinned = pinned
)

fun MyRoutineDTO.toOldRoutine() = OldRoutine(
    id = id,
    title = title,
    description = description,
    doingIt = doingIt,
    createdByUser = createdByUser,
    pinnedDate = pinnedDate
)

fun DayDTO.toOldDay() = OldDay(
    id = id,
    routineId = routineId,
    title = title
)