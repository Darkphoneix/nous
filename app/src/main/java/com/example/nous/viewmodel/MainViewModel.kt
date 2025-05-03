package com.example.nous.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nous.data.model.*
import com.example.nous.repository.NousRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing UI-related data in a lifecycle-conscious way.
 * This ViewModel is responsible for handling user authentication and fetching levels.
 *
 * @param repository The NousRepository instance for data operations.
 */

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: NousRepository
) : ViewModel() {
    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _lectures = MutableLiveData<List<Lecture>>()
    val levels: LiveData<List<Level>> = _lectures

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val result = repository.login(username, password)
                if (result.isSuccess) {
                    _user.value = result.getOrNull()
                } else {
                    _error.value = result.exceptionOrNull()?.message
                }
            } finally {
                _loading.value = false
            }
        }
    }

    fun fetchLevels() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val result = repository.getLevels()
                if (result.isSuccess) {
                    _lectures.value = result.getOrNull()
                } else {
                    _error.value = result.exceptionOrNull()?.message
                }
            } finally {
                _loading.value = false
            }
        }
    }
}