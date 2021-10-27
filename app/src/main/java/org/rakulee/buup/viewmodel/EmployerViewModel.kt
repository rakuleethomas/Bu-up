package org.rakulee.buup.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.parse.ParseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import org.rakulee.buup.fragments.employer.EmployerProfile
import org.rakulee.buup.model.BuupEmployerProfile
import org.rakulee.buup.model.BuupJobSeekerProfile
import org.rakulee.buup.model.EmpInfo
import javax.inject.Inject

@HiltViewModel
class EmployerViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel(){

    private val _currentPts: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(0)
    }
    val currentPts : LiveData<Int> get() = _currentPts

    private fun updatePts(pts : Int){
        _currentPts.postValue(pts)
    }

    private val _employerInfo : MutableLiveData<BuupEmployerProfile> by lazy {
        MutableLiveData<BuupEmployerProfile>()
    }

    val employerInfo : LiveData<BuupEmployerProfile> get() = _employerInfo
    fun updateEmployerInfo(empInfo: BuupEmployerProfile){
        _employerInfo.postValue(empInfo)
    }

    private val _jobSeekerList : MutableLiveData<ArrayList<BuupJobSeekerProfile>> by lazy {
        MutableLiveData<ArrayList<BuupJobSeekerProfile>>()
    }
    val jobSeekerList : LiveData<ArrayList<BuupJobSeekerProfile>> get() = _jobSeekerList
    fun updateJobSeekerList(list: ArrayList<BuupJobSeekerProfile>){
        _jobSeekerList.postValue(list)
    }

    fun fetchCurrentPoint() {
        val pts = ParseUser.getCurrentUser().get("Points") as Int
        updatePts(pts)
    }

    private val _empData : MutableLiveData<EmpInfo> by lazy {
        MutableLiveData<EmpInfo>()
    }

    val emp : LiveData<EmpInfo> get() = _empData


    fun fetchEmployerData(){
        val user = ParseUser.getCurrentUser().get("username") as String
        val pts = ParseUser.getCurrentUser().get("Points") as Int
        _empData.postValue(EmpInfo(user, pts))
    }
}