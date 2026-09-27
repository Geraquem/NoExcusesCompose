package com.mmfsin.noexcusescompose.domain.usecases

import com.mmfsin.noexcusescompose.domain.interfaces.INotesRepository
import javax.inject.Inject

class SaveNoteUseCase @Inject constructor(val repository: INotesRepository) {

    suspend operator fun invoke(noteId: String?, title: String, text: String) =
        repository.saveNote(noteId, title, text)
}
