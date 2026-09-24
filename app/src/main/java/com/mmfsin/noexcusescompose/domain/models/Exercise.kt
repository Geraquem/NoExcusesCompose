package com.mmfsin.noexcusescompose.domain.models

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

fun getExercisesExamples() = listOf(
    Exercise(
        id = "",
        category = "pecho",
        imageURL = "",
        gifURL = "",
        name= "Press banca",
        description = "Descripciónnnnn",
        involvedMuscles = "Involved musclesss",
        isFav = false,
        createdByUser = false
    ),
    Exercise(
        id = "",
        category = "pecho",
        imageURL = "",
        gifURL = "",
        name= "Press militar",
        description = "Descripciónnnnn",
        involvedMuscles = "Involved musclesss",
        isFav = false,
        createdByUser = false
    ),
)