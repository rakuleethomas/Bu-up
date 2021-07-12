package org.rakulee.buup.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.parse.ParseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import org.rakulee.buup.repo.PaymentRepo
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val paymentRepo: PaymentRepo
) : ViewModel(){

    private val _currentSelectedPts: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(0)
    }
    val currentSelectedPts : LiveData<Int> get() = _currentSelectedPts

    fun update(pts : Int){
        _currentSelectedPts.postValue(pts)
    }

    private val _currentPts : MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(0)
    }
    val currentPts : LiveData<Int> get() = _currentPts

    fun fetchCurrentPoint() {
        val pts = ParseUser.getCurrentUser().get("Points") as Int
        _currentPts.postValue(pts)
    }

    fun updateCurrentPoint(pts : Int){
        _currentPts.postValue(pts)
    }

}