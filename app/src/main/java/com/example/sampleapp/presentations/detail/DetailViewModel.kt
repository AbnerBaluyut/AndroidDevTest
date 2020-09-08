package com.example.sampleapp.presentations.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleapp.common.utils.LoadingState
import kotlinx.coroutines.launch

class DetailViewModel(private val detailRepository: DetailRepository) : ViewModel() {

    private val _loadingState = MutableLiveData<LoadingState>()

    val loadingState: LiveData<LoadingState> = _loadingState

    val data = detailRepository.getUsername()

    init {
        fetchData()
    }

    fun saveNote(note: String) {
        detailRepository.saveNote(note)
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                _loadingState.value = LoadingState.LOADING
                detailRepository.refresh()
                _loadingState.value = LoadingState.LOADED
            } catch (e: Exception) {
                _loadingState.value = LoadingState.error(e.message)
            }
        }
    }
}