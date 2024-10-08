package com.example.firstcomposeproject.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firstcomposeproject.domain.usecases.CheckAuthStateUseCase
import com.example.firstcomposeproject.domain.usecases.GetAuthStateUseCase
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinViewModel
class MainViewModel() : ViewModel(), KoinComponent {

    private val getAuthStateUseCase: GetAuthStateUseCase by inject()
    private val checkAuthStateUseCase: CheckAuthStateUseCase by inject()

    val authState = getAuthStateUseCase()

    fun performAuthResult() {
        viewModelScope.launch {
            checkAuthStateUseCase()
        }
    }
}