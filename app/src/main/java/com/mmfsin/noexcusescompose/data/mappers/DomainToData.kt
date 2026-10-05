package com.mmfsin.noexcusescompose.data.mappers

import com.mmfsin.noexcusescompose.data.models.DayDTO
import com.mmfsin.noexcusescompose.data.models.ExerciseRtnDTO
import com.mmfsin.noexcusescompose.data.models.MyRoutineDTO
import com.mmfsin.noexcusescompose.data.models.NoteDTO
import com.mmfsin.noexcusescompose.data.models.SerieDTO
import com.mmfsin.noexcusescompose.domain.models.ExerciseRtn
import com.mmfsin.noexcusescompose.domain.models.Serie
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
    name: String,
    description: String?,
    order: Int
) = MyRoutineDTO(
    id = UUID.randomUUID().toString(),
    name = name,
    description = description,
    order = order
)

fun createDayDTO(
    routineId: String,
    dayId: String,
    name: String,
    order: Int
) = DayDTO(
    id = dayId,
    routineId = routineId,
    name = name,
    order = order,
)

fun ExerciseRtn.toExerciseRtnDTO(order: Int) = ExerciseRtnDTO(
    id = id,
    dayId = dayId,
    exerciseId = exerciseId,
    rest = rest,
    notes = notes,
    superSerie = superSerie,
    order = order,
)

fun Serie.toSerieDTO(exerciseRtnId: String) = SerieDTO(
    id = id,
    exerciseRtnId = exerciseRtnId,
    reps = reps,
    kgs = kgs,
    order = order
)