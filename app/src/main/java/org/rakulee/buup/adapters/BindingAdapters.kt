package org.rakulee.buup.adapters

import android.view.View
import androidx.databinding.BindingAdapter

object BindingAdapters {

    /**
     *  progress bar handling
     * */
    @JvmStatic
    @BindingAdapter("is_loading")
    fun setLoading(view : View, loading : Boolean){

        if(loading){
            view.visibility = View.INVISIBLE
        }else{
            view.visibility = View.VISIBLE
        }
    }
}