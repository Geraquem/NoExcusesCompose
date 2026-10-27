package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.domain.interfaces.INotesRepository
import com.mmfsin.noexcuses.domain.models.Note
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPinnedNoteUseCase @Inject constructor(val repository: INotesRepository) {

    operator fun invoke(): Flow<Note?> = repository.getPinnedNote()
}
