package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.domain.interfaces.IRoutinesRepository
import javax.inject.Inject

class DeleteExerciseRtnUseCase @Inject constructor(val repository: IRoutinesRepository) {

    suspend operator fun invoke(exerciseRtn: String) = repository.deleteExerciseRtn(exerciseRtn)
}
