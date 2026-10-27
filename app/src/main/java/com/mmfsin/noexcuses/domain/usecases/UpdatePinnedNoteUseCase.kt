package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.domain.interfaces.INotesRepository
import javax.inject.Inject

class UpdatePinnedNoteUseCase @Inject constructor(val repository: INotesRepository) {

    operator fun invoke(noteId: String) = repository.updatePinnedNote(noteId)
}
