package com.mmfsin.noexcusescompose.presentation.exercises.favorites

import androidx.lifecycle.viewModelScope
import com.mmfsin.noexcusescompose.domain.usecases.GetFavExercisesUseCase
import com.mmfsin.noexcusescompose.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavExercisesUseCase: GetFavExercisesUseCase,
) : BaseViewModel<FavoritesStates>(FavoritesStates()) {

    init {
        getFavoriteExercises()
    }

    fun getFavoriteExercises() {
        viewModelScope.launch {
            getFavExercisesUseCase().collect { favs ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        favorites = favs
                    )
                }
            }
        }
    }
}