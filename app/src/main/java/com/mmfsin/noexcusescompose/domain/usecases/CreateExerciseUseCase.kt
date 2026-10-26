package com.mmfsin.noexcusescompose.domain.usecases

import com.mmfsin.noexcusescompose.domain.interfaces.IExercisesRepository
import com.mmfsin.noexcusescompose.domain.models.Exercise
import javax.inject.Inject

class CreateExerciseUseCase @Inject constructor(val repository: IExercisesRepository) {

    suspend operator fun invoke(exercise: Exercise) = repository.createExercise(exercise)
}
