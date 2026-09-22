package com.mmfsin.noexcusescompose.data.mappers

import com.mmfsin.noexcusescompose.data.models.ExerciseDTO
import com.mmfsin.noexcusescompose.data.models.MuscularGroupDTO
import com.mmfsin.noexcusescompose.domain.models.Exercise
import com.mmfsin.noexcusescompose.domain.models.MuscularGroup
import kotlin.text.category

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
