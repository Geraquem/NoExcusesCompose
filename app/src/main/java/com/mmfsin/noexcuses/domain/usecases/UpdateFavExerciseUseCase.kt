package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.domain.interfaces.IExercisesRepository
import javax.inject.Inject

class UpdateFavExerciseUseCase @Inject constructor(val repository: IExercisesRepository) {

    operator fun invoke(id: String, value: Boolean) =
        repository.updateFavExercise(id, value)
}
