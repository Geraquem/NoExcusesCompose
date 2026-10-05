package com.mmfsin.noexcusescompose.domain.usecases

import com.mmfsin.noexcusescompose.domain.interfaces.IRoutinesRepository
import com.mmfsin.noexcusescompose.domain.models.ExerciseRtn
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExercisesRtnByDayIdUseCase @Inject constructor(val repository: IRoutinesRepository) {

    suspend operator fun invoke(dayId: String): Flow<List<ExerciseRtn>> =
        repository.getExercisesRtnFromDay(dayId)
}
