package com.mmfsin.noexcusescompose.domain.usecases

import com.mmfsin.noexcusescompose.domain.interfaces.IRmsRepository
import javax.inject.Inject

class DeleteRmUseCase @Inject constructor(val repository: IRmsRepository) {

    operator fun invoke(exerciseId: String) = repository.deleteRm(exerciseId)
}
