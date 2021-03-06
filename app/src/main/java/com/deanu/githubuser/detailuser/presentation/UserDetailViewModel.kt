package com.deanu.githubuser.detailuser.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deanu.githubuser.common.domain.model.UserDetail
import com.deanu.githubuser.common.domain.repository.UserRepository
import com.deanu.githubuser.common.utils.DispatchersProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val dispatchersProvider: DispatchersProvider
) : ViewModel() {

    private val _userDetail = MutableLiveData<UserDetail>()
    val userDetail: LiveData<UserDetail> = _userDetail

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUserDetail(username: String) {
        _isLoading.value = true
        viewModelScope.launch {
            withContext(dispatchersProvider.io()) {
                try {
                    _userDetail.postValue(userRepository.getUserDetail(username))
                } catch (exception: Exception) {
                    _errorMessage.postValue(exception.message)
                }
            }
            _isLoading.postValue(false)
        }
    }
}