package com.mmfsin.migration.mappers

import com.mmfsin.migration.models.convert.OldDay
import com.mmfsin.migration.models.convert.OldExercise
import com.mmfsin.migration.models.convert.OldExerciseRtn
import com.mmfsin.migration.models.convert.OldNote
import com.mmfsin.migration.models.convert.OldRoutine
import com.mmfsin.migration.models.convert.OldSerie
import com.mmfsin.migration.models.realm.ChExerciseDTO
import com.mmfsin.migration.models.realm.DataDTO
import com.mmfsin.migration.models.realm.DayDTO
import com.mmfsin.migration.models.realm.ExerciseDTO
import com.mmfsin.migration.models.realm.MyRoutineDTO
import com.mmfsin.migration.models.realm.NoteDTO

fun NoteDTO.toOldNote() = OldNote(
    id = id,
    title = title,
    description = description,
    date = date,
    pinned = pinned
)

fun ExerciseDTO.toOldExercise() = OldExercise(
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

fun List<ExerciseDTO>.toOldExerciseList() = this.map { it.toOldExercise() }

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

fun ChExerciseDTO.toOldExerciseRtn() = OldExerciseRtn(
    id = id,
    routineId = routineId,
    dayId = dayId,
    exerciseId = exerciseId,
    time = time,
    notes = notes,
    position = position,
    superSerie = superSerie
)

fun List<ChExerciseDTO>.toChExerciseDTOList() = this.map { it.toOldExerciseRtn() }

fun DataDTO.toOldSerie(exerciseRtnId: String) = OldSerie(
    id = id ?: "",
    exerciseRtnId = exerciseRtnId,
    reps = reps,
    weight = weight,
)