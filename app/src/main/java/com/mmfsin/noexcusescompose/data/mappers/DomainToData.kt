package com.mmfsin.noexcusescompose.data.mappers

import com.mmfsin.noexcusescompose.data.models.DayDTO
import com.mmfsin.noexcusescompose.data.models.MyRoutineDTO
import com.mmfsin.noexcusescompose.data.models.NoteDTO
import java.util.UUID

fun createNoteDTO(
    noteId: String?,
    title: String,
    description: String,
    pinned: Boolean
) = NoteDTO(
    id = noteId ?: UUID.randomUUID().toString(),
    title = title,
    description = description,
    date = System.currentTimeMillis(),
    pinned = pinned,
)

fun createRoutineDTO(
    routineId: String,
    name: String,
    description: String?,
) = MyRoutineDTO(
    id = routineId,
    title = name,
    description = description,
    days = 0
)

fun createDayDTO(
    routineId: String,
    name: String,
) = DayDTO(
    id = UUID.randomUUID().toString(),
    routineId = routineId,
    name = name,
    order = 0,
)