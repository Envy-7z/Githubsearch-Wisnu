package com.wisnua.starterproject.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wisnua.starterproject.domain.model.UserItem
import com.wisnua.starterproject.domain.usecase.GetUsersFromLocalUseCase
import com.wisnua.starterproject.domain.usecase.SaveUsersToLocalUseCase
import com.wisnua.starterproject.domain.usecase.SearchUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUserUseCase: SearchUserUseCase,
    private val getUsersFromLocalUseCase: GetUsersFromLocalUseCase,
    private val saveUsersToLocalUseCase: SaveUsersToLocalUseCase
) : ViewModel() {

    private val _searchResults = MutableStateFlow<List<UserItem>>(emptyList())
    val searchResults: StateFlow<List<UserItem>> = _searchResults.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        loadUsersFromLocal()
    }

    private fun loadUsersFromLocal() {
        viewModelScope.launch {
            getUsersFromLocalUseCase()
                .collect { users ->
                    if (users.isNotEmpty()) {
                        _searchResults.value = users
                    }
                }
        }
    }

    fun searchUsers(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val users = searchUserUseCase(query, perPage = 30, page = 1)
                _searchResults.value = users
                saveUsersToLocalUseCase(users)
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
