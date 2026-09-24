package com.mmfsin.noexcusescompose.domain.usecases

import com.mmfsin.noexcusescompose.domain.interfaces.IExercisesRepository
import javax.inject.Inject

class UpdateFavExerciseUseCase @Inject constructor(val repository: IExercisesRepository) {

    operator fun invoke(id: String, value: Boolean) =
        repository.updateFavExercise(id, value)
}
