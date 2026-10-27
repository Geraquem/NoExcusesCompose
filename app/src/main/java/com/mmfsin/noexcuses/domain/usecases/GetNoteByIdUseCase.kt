package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.domain.interfaces.INotesRepository
import com.mmfsin.noexcuses.domain.models.Note
import javax.inject.Inject

class GetNoteByIdUseCase @Inject constructor(val repository: INotesRepository) {

    operator fun invoke(noteId: String): Note? = repository.getNoteById(noteId)
}
