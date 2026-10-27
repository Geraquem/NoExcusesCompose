package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.domain.interfaces.IRmsRepository
import javax.inject.Inject

class DeleteRmUseCase @Inject constructor(val repository: IRmsRepository) {

    operator fun invoke(exerciseId: String) = repository.deleteRm(exerciseId)
}
