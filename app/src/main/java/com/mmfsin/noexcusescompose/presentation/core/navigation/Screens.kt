package com.mmfsin.noexcusescompose.presentation.core.navigation

import kotlinx.serialization.Serializable

@Serializable
object MuscularGroups

@Serializable
data class Exercises(val mGroupId: String)

@Serializable
data class ExerciseDetail(val exerciseId: String)

@Serializable
object Favorites

@Serializable
object Stretch