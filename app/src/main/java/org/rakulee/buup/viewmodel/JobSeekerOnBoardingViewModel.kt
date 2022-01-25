package org.rakulee.buup.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.rakulee.buup.model.BuupJobSeekerProfile
import javax.inject.Inject

@HiltViewModel
class JobSeekerOnBoardingViewModel @Inject constructor(
    private val savedStateHandle : SavedStateHandle
) : ViewModel() {

    private val _buupJobSeekerProfile : MutableLiveData<BuupJobSeekerProfile> by lazy {
        MutableLiveData<BuupJobSeekerProfile>()
    }
    val buupJobSeekerProfile : LiveData<BuupJobSeekerProfile> get() = _buupJobSeekerProfile

    fun updateBuupJobSeekerProfile(buupJobSeekerProfile: BuupJobSeekerProfile){
        _buupJobSeekerProfile.postValue(buupJobSeekerProfile)
    }
}