package com.mmfsin.noexcusescompose.domain.usecases

import com.mmfsin.noexcusescompose.domain.interfaces.IStretchRepository
import com.mmfsin.noexcusescompose.domain.models.Stretch
import javax.inject.Inject

class GetStretchingDataUseCase @Inject constructor(val repository: IStretchRepository) {

    suspend operator fun invoke(): List<Stretch> = repository.getStretchingData()
}
