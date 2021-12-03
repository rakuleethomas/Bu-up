package org.rakulee.buup.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel(){

    private val _isLoading : MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val isLoading : LiveData<Boolean> get() = _isLoading

    fun updateIsLoading(isLoading : Boolean){
        _isLoading.postValue(isLoading)
    }
}