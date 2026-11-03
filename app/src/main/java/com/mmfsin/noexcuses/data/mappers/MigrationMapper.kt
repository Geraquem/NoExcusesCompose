package com.mmfsin.noexcuses.data.mappers

import com.mmfsin.migration.models.convert.OldDay
import com.mmfsin.migration.models.convert.OldExercise
import com.mmfsin.migration.models.convert.OldExerciseRtn
import com.mmfsin.migration.models.convert.OldNote
import com.mmfsin.migration.models.convert.OldRoutine
import com.mmfsin.migration.models.convert.OldSerie
import com.mmfsin.noexcuses.data.models.DayDTO
import com.mmfsin.noexcuses.data.models.ExerciseDTO
import com.mmfsin.noexcuses.data.models.ExerciseRtnDTO
import com.mmfsin.noexcuses.data.models.MyRoutineDTO
import com.mmfsin.noexcuses.data.models.NoteDTO
import com.mmfsin.noexcuses.data.models.SerieDTO
import com.mmfsin.noexcuses.domain.models.RoutineColor.Companion.getRandomRoutineColorId
import com.mmfsin.noexcuses.util.deletePointZero
import java.util.UUID

fun OldNote.toNoteDTO() = NoteDTO(
    id = id,
    title = title,
    description = description,
    date = date,
    pinned = pinned
)

fun OldExercise.toExerciseDTO() = ExerciseDTO(
    id = id,
    category = category,
    imageURL = imageURL,
    gifURL = gifURL,
    name = name,
    order = order,
    description = description,
    muscles = muscles,
    isFav = isFav,
    muscleWikiURL = muscleWikiURL,
    createdByUser = createdByUser,
)

fun OldRoutine.toRoutineDTO(i: Int) = MyRoutineDTO(
    id = id,
    name = title,
    description = description,
    pinned = doingIt,
    createdByUser = createdByUser,
    pinnedDate = pinnedDate,
    order = i,
    colorId = getRandomRoutineColorId()
)

fun OldDay.toDayDTO(i: Int) = DayDTO(
    id = id,
    routineId = routineId,
    name = title,
    order = i
)

fun OldExerciseRtn.toExerciseRtnDTO(index: Int) = ExerciseRtnDTO(
    id = id,
    dayId = dayId,
    exerciseId = exerciseId ?: "",
    rest = time.deletePointZero(),
    notes = notes,
    superSerie = superSerie,
    order = index
)

fun OldSerie.toSerieDTO(index: Int) = SerieDTO(
    id = id,
    exerciseRtnId = exerciseRtnId,
    reps = reps,
    kgs = weight?.deletePointZero(),
    order = index,
)