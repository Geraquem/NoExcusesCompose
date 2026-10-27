package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.domain.interfaces.IRoutinesRepository
import com.mmfsin.noexcuses.domain.models.ExerciseRtn
import javax.inject.Inject

class GetDfDayExercisesUseCase @Inject constructor(val repository: IRoutinesRepository) {

    suspend operator fun invoke(routineId: String, dayId: String): List<ExerciseRtn> =
        repository.getDfDayExercises(routineId, dayId)
}
