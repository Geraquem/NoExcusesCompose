package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.domain.interfaces.IRmsRepository
import javax.inject.Inject

class DeleteRmDataUseCase @Inject constructor(val repository: IRmsRepository) {

    operator fun invoke(rmId: String) = repository.deleteRmData(rmId)
}
