package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.domain.interfaces.IRoutinesRepository
import com.mmfsin.noexcuses.domain.models.ExerciseRtn
import javax.inject.Inject

class AddExerciseToDayUseCase @Inject constructor(val repository: IRoutinesRepository) {

    suspend operator fun invoke(exercise: ExerciseRtn) = repository.addExerciseToDay(exercise)
}
