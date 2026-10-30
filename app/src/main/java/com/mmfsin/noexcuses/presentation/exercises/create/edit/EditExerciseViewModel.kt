package com.mmfsin.noexcuses.presentation.exercises.create.edit

import android.content.Context
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mmfsin.noexcuses.domain.models.Exercise
import com.mmfsin.noexcuses.domain.usecases.CreateExerciseUseCase
import com.mmfsin.noexcuses.domain.usecases.DeleteExerciseUseCase
import com.mmfsin.noexcuses.domain.usecases.GetExerciseByIdUseCase
import com.mmfsin.noexcuses.presentation.core.base.BaseViewModel
import com.mmfsin.noexcuses.util.copyImageToInternalStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditExerciseViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getExerciseByIdUseCase: GetExerciseByIdUseCase,
    private val createExerciseUseCase: CreateExerciseUseCase,
    private val deleteExerciseUseCase: DeleteExerciseUseCase,
) : BaseViewModel<EditExerciseStates>(EditExerciseStates()) {

    private val exerciseId: String? = savedStateHandle["exerciseId"]

    init {
        exerciseId?.let { getExercise(it) } ?: run { sww() }
    }

    fun getExercise(exerciseId: String) {
        viewModelScope.launch {
            getExerciseByIdUseCase(exerciseId).collect { exercise ->
                exercise?.let { e ->
                    _uiState.update {
                        it.copy(
                            exerciseId = exerciseId,
                            imageUrl = e.gifURL ?: "",
                            mGroupId = e.category,
                            name = e.name,
                            description = e.description,
                            involvedMuscles = e.involvedMuscles,
                            isFav = e.isFav
                        )
                    }
                } ?: run { sww() }
            }
        }
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

    fun editExercise() {
        val states = uiState.value
        if (states.name.isBlank()) emptyNameError(true)
        else {
            emptyNameError(true)

            states.exerciseId?.let { id ->
                val exercise = Exercise(
                    id = id,
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
            } ?: run { sww() }
        }
    }

    fun deleteExercise() {
        val states = uiState.value
        states.exerciseId?.let { id ->
            executeUseCase(
                { deleteExerciseUseCase(id) },
                {
                    showDeleteExercise(false)
                    shouldGoBack(true)
                },
                { sww() }
            )
        } ?: run { sww() }
    }

    fun showDeleteExercise(value: Boolean) = _uiState.update { it.copy(showDeleteExercise = value) }
    fun shouldGoBack(value: Boolean) = _uiState.update { it.copy(shouldGoBack = value) }
    fun emptyNameError(value: Boolean) = _uiState.update { it.copy(emptyNameError = value) }
    fun sww(value: Boolean = true) = _uiState.update { it.copy(sww = value) }
}