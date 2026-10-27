package com.mmfsin.noexcusescompose.presentation.exercises.create.edit

data class EditExerciseStates(
    val isLoading: Boolean = true,

    val showDeleteExercise: Boolean = false,

    val exerciseId: String? = null,

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