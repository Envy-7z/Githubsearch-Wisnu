package com.wisnua.starterproject.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wisnua.starterproject.domain.model.DetailResponse
import com.wisnua.starterproject.domain.model.RepoResponseItem
import com.wisnua.starterproject.domain.usecase.GetDetailUserUseCase
import com.wisnua.starterproject.domain.usecase.GetUserReposUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val getDetailUserUseCase: GetDetailUserUseCase,
    private val getUserReposUseCase: GetUserReposUseCase
) : ViewModel() {

    private val _userDetail = MutableStateFlow<DetailResponse?>(null)
    val userDetail: StateFlow<DetailResponse?> get() = _userDetail

    private val _repositories = MutableStateFlow<List<RepoResponseItem>>(emptyList())
    val repositories: StateFlow<List<RepoResponseItem>> get() = _repositories

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    fun getUserDetail(username: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _userDetail.value = getDetailUserUseCase(username)
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getUserRepos(username: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _repositories.value = getUserReposUseCase(username)
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
