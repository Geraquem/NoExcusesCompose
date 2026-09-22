package com.mmfsin.noexcusescompose.presentation.menu

import com.mmfsin.noexcusescompose.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(

) : BaseViewModel<MenuStates>(MenuStates()) {

    init {

    }

}