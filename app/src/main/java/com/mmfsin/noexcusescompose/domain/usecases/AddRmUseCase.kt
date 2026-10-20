package com.mmfsin.noexcusescompose.domain.usecases

import com.mmfsin.noexcusescompose.domain.interfaces.IRmsRepository
import javax.inject.Inject

class AddRmUseCase @Inject constructor(val repository: IRmsRepository) {

    suspend operator fun invoke(exerciseId: String, date: Long, kgs: Double) =
        repository.addRm(exerciseId, date, kgs)
}
