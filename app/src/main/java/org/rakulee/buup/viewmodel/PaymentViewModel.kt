package org.rakulee.buup.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.rakulee.buup.repo.PaymentRepo
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor() : ViewModel(){

    private val _currentSelectedPts: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(0)
    }
    val currentSelectedPts : LiveData<Int> get() = _currentSelectedPts



    fun update(pts : Int){
        _currentSelectedPts.postValue(pts)
    }


}