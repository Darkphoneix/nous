package com.example.nous.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nous.data.model.User
import com.example.nous.data.model.UserRole
import com.example.nous.repository.NousRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.nous.data.model.Lecture

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
    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> = _user

    private val _levels = MutableLiveData<List<Lecture>>()
    val levels: LiveData<List<Lecture>> = _levels

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val result = repository.login(email, password)
                if (result.isSuccess) {
                    val authResponse = result.getOrNull()
                    _user.value = authResponse?.let { response ->
                        User(
                            id = response.Id,
                            email = response.email,
                            username = response.username,
                            role = UserRole.valueOf(response.role.uppercase()),
                            createdAt = response.createdAt
                        )
                    }
                } else {
                    _error.value = result.exceptionOrNull()?.message
                }
            } finally {
                _loading.value = false
            }
        }
    }

    fun fetchLectures() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val result = repository.getLevels()
                if (result.isSuccess) {
                    _levels.value = result.getOrNull()
                } else {
                    _error.value = result.exceptionOrNull()?.message
                }
            } finally {
                _loading.value = false
            }
        }
    }
}