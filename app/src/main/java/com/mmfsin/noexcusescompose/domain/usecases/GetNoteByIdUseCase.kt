package com.mmfsin.noexcusescompose.domain.usecases

import com.mmfsin.noexcusescompose.domain.interfaces.INotesRepository
import com.mmfsin.noexcusescompose.domain.models.Note
import javax.inject.Inject

class GetNoteByIdUseCase @Inject constructor(val repository: INotesRepository) {

    operator fun invoke(noteId: String): Note? = repository.getNoteById(noteId)
}
