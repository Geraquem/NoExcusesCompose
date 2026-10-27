package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.domain.interfaces.IRoutinesRepository
import com.mmfsin.noexcuses.domain.models.ExerciseRtn
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExercisesRtnByDayIdUseCase @Inject constructor(val repository: IRoutinesRepository) {

    operator fun invoke(dayId: String): Flow<List<ExerciseRtn>> =
        repository.getExercisesRtnFromDay(dayId)
}
