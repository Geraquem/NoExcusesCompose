package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.domain.interfaces.IExercisesRepository
import com.mmfsin.noexcuses.domain.models.Exercise
import javax.inject.Inject

class CreateExerciseUseCase @Inject constructor(val repository: IExercisesRepository) {

    suspend operator fun invoke(exercise: Exercise) = repository.createExercise(exercise)
}
