package com.mmfsin.noexcusescompose.presentation.core.navigation

import kotlinx.serialization.Serializable

@Serializable
object MuscularGroups

@Serializable
data class Exercises(val mGroupId: String)

@Serializable
data class ExerciseDetail(val exerciseId: String)

/************* MY ROUTINES ****************/

@Serializable
object MyRoutines

@Serializable
data class DayDetail(val routineId: String, val dayId: String?)

@Serializable
data class MuscularGroupsRtn(val dayId: String, val dayName: String)

/******************************************/

@Serializable
object Favorites

@Serializable
object Stretch

@Serializable
data class NoteDetail(val noteId: String?)