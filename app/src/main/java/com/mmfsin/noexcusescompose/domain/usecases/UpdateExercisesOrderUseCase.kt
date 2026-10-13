package com.mmfsin.noexcusescompose.domain.usecases

import com.mmfsin.noexcusescompose.domain.interfaces.IRoutinesRepository
import com.mmfsin.noexcusescompose.domain.models.ExerciseRtn
import javax.inject.Inject

class UpdateExercisesOrderUseCase @Inject constructor(
    private val repository: IRoutinesRepository
) {
    suspend operator fun invoke(exercises: List<ExerciseRtn>) =
        repository.updateExercisesOrder(exercises)
}
