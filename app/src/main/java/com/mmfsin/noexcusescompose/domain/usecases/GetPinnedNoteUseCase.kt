package com.mmfsin.noexcusescompose.domain.usecases

import com.mmfsin.noexcusescompose.domain.interfaces.INotesRepository
import com.mmfsin.noexcusescompose.domain.models.Note
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPinnedNoteUseCase @Inject constructor(val repository: INotesRepository) {

    operator fun invoke(): Flow<Note?> = repository.getPinnedNote()
}
