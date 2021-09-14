package org.rakulee.buup.util

import android.content.Context
import android.util.DisplayMetrics

object Util {

    fun pxToDp(px : Double, context : Context) : Double {
         return (1.0 * px * (context.resources.displayMetrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT)
    }
    fun pxToDp(px : Int, context : Context) : Double {
        return (1.0 * px * (context.resources.displayMetrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun dpToPx(dp : Double, context: Context) : Double{
        return 1.0 * dp * (context.resources.displayMetrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT
    }

    fun dpToPx(dp : Int, context: Context) : Double{
        return 1.0 * dp *(context.resources.displayMetrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT
    }
}