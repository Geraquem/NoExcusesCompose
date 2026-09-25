package com.mmfsin.noexcusescompose.domain.usecases

import com.mmfsin.noexcusescompose.domain.interfaces.INotesRepository
import com.mmfsin.noexcusescompose.domain.models.Note
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(val repository: INotesRepository) {

    suspend operator fun invoke(): Flow<List<Note>> = repository.getNotes()
}
