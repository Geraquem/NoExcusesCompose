package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.domain.interfaces.INotesRepository
import javax.inject.Inject

class SaveNoteUseCase @Inject constructor(val repository: INotesRepository) {

    suspend operator fun invoke(
        noteId: String?,
        title: String,
        text: String,
        pinned: Boolean
    ) = repository.saveNote(noteId, title, text, pinned)
}
