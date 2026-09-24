package com.mmfsin.noexcusescompose.domain.usecases

import com.mmfsin.noexcusescompose.domain.interfaces.IExercisesRepository
import com.mmfsin.noexcusescompose.domain.models.Exercise
import javax.inject.Inject

class GetExercisesByMGroupUseCase @Inject constructor(val repository: IExercisesRepository) {

    suspend operator fun invoke(mGroupId: String): List<Exercise> {
        val exercises = repository.getExercises()
        return exercises.filter { it.category == mGroupId }
    }
}
