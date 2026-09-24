package com.mmfsin.noexcusescompose.domain.usecases

import com.mmfsin.noexcusescompose.domain.interfaces.IExercisesRepository
import com.mmfsin.noexcusescompose.domain.models.Exercise
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExercisesByMGroupUseCase @Inject constructor(val repository: IExercisesRepository) {

    suspend operator fun invoke(mGroupId: String): Flow<List<Exercise>> =
        repository.getExercisesByMuscularGroup(mGroupId)
}
