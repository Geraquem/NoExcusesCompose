package com.mmfsin.noexcuses.presentation.exercises.create

data class CreateExerciseStates(
    val isLoading: Boolean = true,

    val imageUrl: String = "",
    val name: String = "",
    val mGroupId: String = "",
    val description: String = "",
    val involvedMuscles: String = "",
    val isFav: Boolean = false,

    val shouldGoBack: Boolean = false,
    val emptyNameError: Boolean = false,
    val sww: Boolean = false
)