package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.domain.interfaces.IRmsRepository
import javax.inject.Inject

class AddRmUseCase @Inject constructor(val repository: IRmsRepository) {

    suspend operator fun invoke(exerciseId: String, date: Long, kgs: Double) =
        repository.addRm(exerciseId, date, kgs)
}
