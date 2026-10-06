package com.mmfsin.noexcusescompose.domain.usecases

import com.mmfsin.noexcusescompose.domain.interfaces.IRoutinesRepository
import com.mmfsin.noexcusescompose.domain.models.ExerciseRtn
import javax.inject.Inject

class EditExerciseRtnUseCase @Inject constructor(val repository: IRoutinesRepository) {

    suspend operator fun invoke(exerciseRtn: ExerciseRtn) = repository.editExerciseRtn(exerciseRtn)
}
