package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.domain.interfaces.IMenuRepository
import javax.inject.Inject

class CheckVersionUseCase @Inject constructor(val repository: IMenuRepository) {

    suspend operator fun invoke() = repository.checkVersion()
}
