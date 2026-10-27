package com.mmfsin.noexcuses.data.mappers

import com.mmfsin.noexcuses.data.models.DayDTO
import com.mmfsin.noexcuses.data.models.ExerciseDTO
import com.mmfsin.noexcuses.data.models.ExerciseRtnDTO
import com.mmfsin.noexcuses.data.models.MyRoutineDTO
import com.mmfsin.noexcuses.data.models.NoteDTO
import com.mmfsin.noexcuses.data.models.RmDTO
import com.mmfsin.noexcuses.data.models.SerieDTO
import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.domain.models.ExerciseRtn
import com.mmfsin.noexcuses.domain.models.RoutineColor.Companion.getRandomRoutineColorId
import com.mmfsin.noexcuses.domain.models.Serie
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
    createdByUser = true,
    order = order,
    colorId = getRandomRoutineColorId()
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

fun createRmDTO(exerciseId: String, date: Long, kgs: Double) = RmDTO(
    id = UUID.randomUUID().toString(),
    exerciseId = exerciseId,
    date = date,
    kgs = kgs
)

fun Exercise.createExerciseDTO() = ExerciseDTO(
    id = id,
    category = category,
    imageURL = imageURL,
    gifURL = imageURL,
    name = name,
    order = 99,
    description = description,
    muscles = involvedMuscles,
    isFav = isFav,
    createdByUser = true,
)

fun Day.toDayDTO() = DayDTO(
    id = id,
    routineId = routineId,
    name = name,
    order = order
)

fun List<Day>.toDayDTOList() = this.map { it.toDayDTO() }

fun ExerciseRtn.toExerciseRtnDTO() = ExerciseRtnDTO(
    id = id,
    dayId = dayId,
    exerciseId = exerciseId,
    rest = rest,
    notes = null,
    superSerie = superSerie,
    order = order
)

fun List<ExerciseRtn>.toExerciseRtnList() = this.map { it.toExerciseRtnDTO() }