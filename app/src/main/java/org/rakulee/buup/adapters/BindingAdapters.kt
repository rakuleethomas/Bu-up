package org.rakulee.buup.adapters

import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorInt
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

    @JvmStatic
    @BindingAdapter("app:tint")
    fun ImageView.setImageTint(@ColorInt color: Int) {
        setColorFilter(color)
    }
}