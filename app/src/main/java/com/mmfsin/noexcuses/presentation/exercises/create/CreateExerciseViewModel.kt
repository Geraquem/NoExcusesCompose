package com.mmfsin.noexcuses.presentation.exercises.create

import android.content.Context
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.domain.usecases.CreateExerciseUseCase
import com.mmfsin.noexcuses.presentation.core.base.BaseViewModel
import com.mmfsin.noexcuses.util.copyImageToInternalStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateExerciseViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val createExerciseUseCase: CreateExerciseUseCase,
) : BaseViewModel<CreateExerciseStates>(CreateExerciseStates()) {

    private val mGroupId: String? = savedStateHandle["mGroupId"]

    init {
        mGroupId?.let { setMGroup(it) }
    }

    fun setMGroup(mGroupId: String) {
        _uiState.update { it.copy(mGroupId = mGroupId) }
    }

    fun editImageUrl(uri: Uri, context: Context) {
        viewModelScope.launch {
            val path = copyImageToInternalStorage(
                context = context,
                uri = uri
            )
            _uiState.update { it.copy(imageUrl = path) }
        }
    }

    fun editName(value: String) = _uiState.update { it.copy(name = value) }
    fun editDescription(value: String) = _uiState.update { it.copy(description = value) }
    fun editInvolvedMuscles(value: String) = _uiState.update { it.copy(involvedMuscles = value) }
    fun editFav(value: Boolean) = _uiState.update { it.copy(isFav = value) }

    fun createExercise() {
        val states = uiState.value
        if (states.name.isBlank()) emptyNameError(true)
        else {
            emptyNameError(true)

            val exercise = Exercise(
                id = UUID.randomUUID().toString(),
                category = states.mGroupId,
                imageURL = states.imageUrl,
                gifURL = states.imageUrl,
                name = states.name,
                description = states.description,
                involvedMuscles = states.involvedMuscles,
                isFav = states.isFav,
                createdByUser = true
            )

            executeUseCase(
                { createExerciseUseCase(exercise) },
                { shouldGoBack(true) },
                { sww() },
            )
        }
    }

    fun shouldGoBack(value: Boolean) = _uiState.update { it.copy(shouldGoBack = value) }
    fun emptyNameError(value: Boolean) = _uiState.update { it.copy(emptyNameError = value) }
    fun sww(value: Boolean = true) = _uiState.update { it.copy(sww = value) }
}