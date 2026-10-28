package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.domain.interfaces.IMenuRepository
import com.mmfsin.noexcuses.domain.models.MenuImages
import javax.inject.Inject

class GetMenuImagesUseCase @Inject constructor(val repository: IMenuRepository) {

    suspend operator fun invoke(): MenuImages? = repository.getMenuImages()
}
