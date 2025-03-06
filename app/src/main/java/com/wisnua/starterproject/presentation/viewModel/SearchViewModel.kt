package com.wisnua.starterproject.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wisnua.starterproject.domain.model.UserItem
import com.wisnua.starterproject.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _searchResults = MutableStateFlow<List<UserItem>>(emptyList())
    val searchResults: StateFlow<List<UserItem>> = _searchResults

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun searchUsers(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val users = userRepository.searchUsers(query, perPage = 30, page = 1)
                _searchResults.value = users
            } catch (e: Exception) {
                _errorMessage.value = e.localizedMessage
            } finally {
                _isLoading.value = false
            }
        }
    }
}
