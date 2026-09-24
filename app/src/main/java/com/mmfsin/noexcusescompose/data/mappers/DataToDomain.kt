package com.mmfsin.noexcusescompose.data.mappers

import com.mmfsin.noexcusescompose.data.models.ExerciseDTO
import com.mmfsin.noexcusescompose.data.models.MuscularGroupDTO
import com.mmfsin.noexcusescompose.data.models.StretchDTO
import com.mmfsin.noexcusescompose.domain.models.Exercise
import com.mmfsin.noexcusescompose.domain.models.MuscularGroup
import com.mmfsin.noexcusescompose.domain.models.Stretch
import com.mmfsin.noexcusescompose.domain.models.Stretching

fun MuscularGroupDTO.toMuscularGroup() = MuscularGroup(
    id = id,
    name = name,
    manImageURL = manImageURL,
    womanImageURL = womanImageURL
)

fun List<MuscularGroupDTO>.toMuscularGroupList() = this.map { it.toMuscularGroup() }

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

/***************/
/** STRETCHING */
/***************/
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
