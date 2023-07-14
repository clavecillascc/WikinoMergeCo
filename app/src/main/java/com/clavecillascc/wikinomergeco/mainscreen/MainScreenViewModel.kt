package com.clavecillascc.wikinomergeco.mainScreen


import androidx.lifecycle.ViewModel
import com.clavecillascc.wikinomergeco.repository.StorageRepository

class MainScreenViewModel(
    private val repository: StorageRepository = StorageRepository(),
) : ViewModel() {
    fun signOut() = repository.signOut()
}