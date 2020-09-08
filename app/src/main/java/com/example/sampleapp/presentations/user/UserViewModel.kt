package com.example.sampleapp.presentations.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sampleapp.common.utils.LoadingState
import androidx.lifecycle.viewModelScope
import com.example.sampleapp.common.utils.handler
import kotlinx.coroutines.launch

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _loadingState = MutableLiveData<LoadingState>()

    val loadingState: LiveData<LoadingState> =  _loadingState

    val data = userRepository.data

    init {
        fetchData()
    }

    private fun fetchData() {

        viewModelScope.launch {
            try {
                _loadingState.value = LoadingState.LOADING
                userRepository.refresh()
                _loadingState.value = LoadingState.LOADED
            } catch (e: Exception) {
                _loadingState.value = LoadingState.error(e.message)
            }
        }
    }
}