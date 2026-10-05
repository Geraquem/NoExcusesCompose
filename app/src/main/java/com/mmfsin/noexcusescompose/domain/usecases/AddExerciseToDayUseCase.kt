package com.mmfsin.noexcusescompose.domain.usecases

import com.mmfsin.noexcusescompose.domain.interfaces.IRoutinesRepository
import com.mmfsin.noexcusescompose.domain.models.ExerciseRtn
import javax.inject.Inject

class AddExerciseToDayUseCase @Inject constructor(val repository: IRoutinesRepository) {

    suspend operator fun invoke(exercise: ExerciseRtn) = repository.addExerciseToDay(exercise)
}
