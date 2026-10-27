package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.domain.interfaces.IRoutinesRepository
import com.mmfsin.noexcuses.domain.models.ExerciseRtn
import javax.inject.Inject

class UpdateExercisesOrderUseCase @Inject constructor(
    private val repository: IRoutinesRepository
) {
    suspend operator fun invoke(exercises: List<ExerciseRtn>) =
        repository.updateExercisesOrder(exercises)
}
