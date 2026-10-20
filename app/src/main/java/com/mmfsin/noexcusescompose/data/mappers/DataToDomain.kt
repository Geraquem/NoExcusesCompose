package com.mmfsin.noexcusescompose.data.mappers

import com.mmfsin.noexcusescompose.data.models.DayDTO
import com.mmfsin.noexcusescompose.data.models.ExerciseDTO
import com.mmfsin.noexcusescompose.data.models.ExerciseRtnDTO
import com.mmfsin.noexcusescompose.data.models.ExerciseRtnWithSeries
import com.mmfsin.noexcusescompose.data.models.MuscularGroupDTO
import com.mmfsin.noexcusescompose.data.models.MyRoutineDTO
import com.mmfsin.noexcusescompose.data.models.NoteDTO
import com.mmfsin.noexcusescompose.data.models.RmWithExercise
import com.mmfsin.noexcusescompose.data.models.RoutineWithDays
import com.mmfsin.noexcusescompose.data.models.SerieDTO
import com.mmfsin.noexcusescompose.data.models.StretchDTO
import com.mmfsin.noexcusescompose.domain.models.Day
import com.mmfsin.noexcusescompose.domain.models.Exercise
import com.mmfsin.noexcusescompose.domain.models.ExerciseRtn
import com.mmfsin.noexcusescompose.domain.models.MuscularGroup
import com.mmfsin.noexcusescompose.domain.models.Note
import com.mmfsin.noexcusescompose.domain.models.RmData
import com.mmfsin.noexcusescompose.domain.models.Routine
import com.mmfsin.noexcusescompose.domain.models.RoutineColor.Companion.getRoutineColorById
import com.mmfsin.noexcusescompose.domain.models.Serie
import com.mmfsin.noexcusescompose.domain.models.Stretch
import com.mmfsin.noexcusescompose.domain.models.Stretching
import com.mmfsin.noexcusescompose.util.formatDate

/*********************************************************************************************************/
/******************************************  MUSCULAR GROUPS  ********************************************/
/*********************************************************************************************************/

fun MuscularGroupDTO.toMuscularGroup() = MuscularGroup(
    id = id,
    name = name,
    manImageURL = manImageURL,
    womanImageURL = womanImageURL
)

fun List<MuscularGroupDTO>.toMuscularGroupList() = this.map { it.toMuscularGroup() }

/*********************************************************************************************************/
/*********************************************  EXERCISES  ***********************************************/
/*********************************************************************************************************/

fun ExerciseDTO.toExercise() = Exercise(
    id = id,
    category = category,
    imageURL = imageURL,
    gifURL = gifURL,
    name = name,
    description = description,
    involvedMuscles = muscles,
    isFav = isFav,
    createdByUser = createdByUser
)

fun List<ExerciseDTO>.toExerciseList() = this.map { it.toExercise() }

/*********************************************************************************************************/
/**********************************************  ROUTINES  ***********************************************/
/*********************************************************************************************************/

fun RoutineWithDays.toRoutine(days: List<Day>) = Routine(
    id = routine.id,
    name = routine.name,
    description = routine.description,
    days = days,
    pinned = routine.pinned,
    createdByUser = routine.createdByUser,
    pinnedDate = formatDate(routine.pinnedDate),
    order = routine.order,
    color = getRoutineColorById(routine.colorId)
)

fun MyRoutineDTO.toRoutine(days: List<Day>) = Routine(
    id = id,
    name = name,
    description = description,
    days = days,
    pinned = pinned,
    createdByUser = createdByUser,
    pinnedDate = formatDate(pinnedDate),
    order = order,
    color = getRoutineColorById(colorId)
)

fun DayDTO.toDay() = Day(
    id = id,
    routineId = routineId,
    name = name,
    order = order
)

fun List<DayDTO>.toDayList() = this.map { it.toDay() }.sortedBy { it.order }

fun ExerciseRtnWithSeries.toExerciseRtn() = ExerciseRtn(
    id = exerciseRtn.id,
    dayId = exerciseRtn.dayId,
    exerciseId = exerciseRtn.exerciseId,
    exercise = exercise?.toExercise(),
    series = series.map { it.toSerie() },
    rest = exerciseRtn.rest,
    notes = exerciseRtn.notes,
    superSerie = exerciseRtn.superSerie,
    order = exerciseRtn.order
)

fun createDfExerciseRtnDTO(
    id: String,
    dayId: String,
    exerciseId: String,
    rest: String,
    series: String,
    superSerie: Boolean,
    order: Int
) = ExerciseRtnDTO(
    id = id,
    dayId = dayId,
    exerciseId = exerciseId,
    rest = rest,
    notes = series,
    superSerie = superSerie,
    order = order
)

fun SerieDTO.toSerie() = Serie(
    id = id,
    reps = reps,
    kgs = kgs,
    order = order
)

fun List<ExerciseRtnWithSeries>.toExerciseRtnList() = this.map { it.toExerciseRtn() }

/*********************************************************************************************************/
/*********************************************  STRETCHING  **********************************************/
/*********************************************************************************************************/

fun List<StretchDTO>.toStretchList(): List<Stretch> {
    val result = mutableListOf<Stretch>()
    val list = this.groupBy { it.category }
    list.forEach { data ->
        val (mGroup, stretching) = data
        val stretch = Stretch(
            mGroup = mGroup,
            stretching = stretching.toStretching().sortedBy { it.order }
        )
        result.add(stretch)
    }
    return result
}

fun StretchDTO.toStretching() = Stretching(
    imageURL = imageURL,
    description = description,
    order = order
)

fun List<StretchDTO>.toStretching() = this.map { it.toStretching() }

/*********************************************************************************************************/
/***********************************************  NOTES  *************************************************/
/*********************************************************************************************************/

fun NoteDTO.toNote() = Note(
    id = id,
    title = title,
    description = description,
    date = formatDate(date) ?: "",
    pinned = pinned
)

fun List<NoteDTO>.toNoteList() = this.map { it.toNote() }

/*********************************************************************************************************/
/***********************************************  RMS  ***************************************************/
/*********************************************************************************************************/

fun RmWithExercise.toRmData() = RmData(
    id = rmDTO.id,
    kgs = rmDTO.kgs,
    date = formatDate(rmDTO.date)
)