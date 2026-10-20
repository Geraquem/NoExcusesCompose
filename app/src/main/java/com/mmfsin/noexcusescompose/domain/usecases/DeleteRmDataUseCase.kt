package com.mmfsin.noexcusescompose.domain.usecases

import com.mmfsin.noexcusescompose.domain.interfaces.IRmsRepository
import javax.inject.Inject

class DeleteRmDataUseCase @Inject constructor(val repository: IRmsRepository) {

    operator fun invoke(rmId: String) = repository.deleteRmData(rmId)
}
