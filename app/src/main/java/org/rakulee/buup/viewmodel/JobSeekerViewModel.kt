package org.rakulee.buup.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.rakulee.buup.model.BuupEmployerProfile
import org.rakulee.buup.model.BuupGetJobPostingByDistanceResponse
import org.rakulee.buup.model.BuupJobSeekerProfile
import javax.inject.Inject

@HiltViewModel
class JobSeekerViewModel @Inject constructor (
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _buupJobSeekerProfile : MutableLiveData<BuupJobSeekerProfile> by lazy {
        MutableLiveData<BuupJobSeekerProfile>()
    }
    val buupJobSeekerProfile : LiveData<BuupJobSeekerProfile> get() = _buupJobSeekerProfile

    fun updateBuupJobSeekerProfile(buupJobSeekerProfile: BuupJobSeekerProfile){
        _buupJobSeekerProfile.postValue(buupJobSeekerProfile)
    }

    private val _currentJobListByDistance : MutableLiveData<ArrayList<BuupGetJobPostingByDistanceResponse.BuupGetJobPostingByDistanceResponseItem>> by lazy {
        MutableLiveData<ArrayList<BuupGetJobPostingByDistanceResponse.BuupGetJobPostingByDistanceResponseItem>>()
    }

    val currentJobListByDistance : LiveData<ArrayList<BuupGetJobPostingByDistanceResponse.BuupGetJobPostingByDistanceResponseItem>> get() = _currentJobListByDistance

    fun updateCurrentJobListByDistance(list : ArrayList<BuupGetJobPostingByDistanceResponse.BuupGetJobPostingByDistanceResponseItem>){
        _currentJobListByDistance.postValue(list)
    }


}