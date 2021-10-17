package org.rakulee.buup.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.rakulee.buup.model.BuupEmployerProfile
import javax.inject.Inject

@HiltViewModel
class EmployerOnBoardingViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _buupEmployerProfile : MutableLiveData<BuupEmployerProfile> by lazy {
        MutableLiveData<BuupEmployerProfile>()
    }
    val buupEmployerProfile : LiveData<BuupEmployerProfile> get() = _buupEmployerProfile

    fun updateBuupEmployerProfile(buupEmployerProfile: BuupEmployerProfile){
        _buupEmployerProfile.postValue(buupEmployerProfile)
    }


}