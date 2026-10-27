package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.domain.interfaces.IStretchRepository
import com.mmfsin.noexcuses.domain.models.Stretch
import javax.inject.Inject

class GetStretchingDataUseCase @Inject constructor(val repository: IStretchRepository) {

    suspend operator fun invoke(): List<Stretch> = repository.getStretchingData()
}
