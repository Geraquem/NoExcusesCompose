package com.mmfsin.noexcusescompose.domain.models

import com.mmfsin.noexcusescompose.util.CREATE_EXERCISE

data class Exercise(
    var id: String,
    var category: String,
    var imageURL: String,
    var gifURL: String?,
    var name: String,
    var description: String,
    var involvedMuscles: String,
    var isFav: Boolean,
    var createdByUser: Boolean
)

fun buttonCreateExercise() = Exercise(
    id = CREATE_EXERCISE,
    category = "",
    imageURL = "",
    gifURL = "",
    name = "",
    description = "",
    involvedMuscles = "",
    isFav = false,
    createdByUser = false
)

fun getExercisesExamples() = listOf(
    Exercise(
        id = "1",
        category = "pecho",
        imageURL = "",
        gifURL = "",
        name = "Aperturas con mancuernas",
        description = "",
        involvedMuscles = "Involved musclesss",
        isFav = false,
        createdByUser = true
    ),
    Exercise(
        id = "2",
        category = "pecho",
        imageURL = "",
        gifURL = "",
        name = "Press militar",
        description = "Descripciónnnnn",
        involvedMuscles = "Involved musclesss",
        isFav = true,
        createdByUser = false
    ),
    buttonCreateExercise()
)