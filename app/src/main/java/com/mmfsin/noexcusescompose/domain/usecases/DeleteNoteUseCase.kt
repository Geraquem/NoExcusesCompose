package com.mmfsin.noexcusescompose.domain.usecases

import com.mmfsin.noexcusescompose.domain.interfaces.INotesRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(val repository: INotesRepository) {

    operator fun invoke(noteId: String) = repository.deleteNote(noteId)
}
