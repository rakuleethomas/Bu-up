package org.rakulee.buup.screens

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.Request
import okio.Timeout
import org.rakulee.buup.BuildConfig
import org.rakulee.buup.R
import org.rakulee.buup.databinding.ActivityPaymentBinding
import org.rakulee.buup.model.ChargeResult
import org.rakulee.buup.module.NetworkModule
import org.rakulee.buup.network.service.CardEntryBackgroundHandler
import org.rakulee.buup.network.service.SquareAPI
import org.rakulee.buup.repo.PaymentRepo
import org.rakulee.buup.viewmodel.PaymentViewModel
import retrofit2.Response
import retrofit2.Retrofit
import sqip.Call
import sqip.Callback
import sqip.CardEntry
import sqip.CardEntryActivityResult
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@AndroidEntryPoint
class PaymentActivity : AppCompatActivity() {

    lateinit var binding : ActivityPaymentBinding
    private val viewModel : PaymentViewModel by viewModels()
    lateinit var call : retrofit2.Call<ChargeResult>

    @Inject
    lateinit var paymentRepo : PaymentRepo

    @Inject
    lateinit var squareAPI: SquareAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.ui = this
        binding.points = 100

        binding.sliderPts.addOnChangeListener {slider, value, fromUser ->
            binding.viewModel!!.update(value.toInt())
            CardEntryBackgroundHandler.points = value.toInt().toString()
        }


    }
    
    fun doCheckOut(){

        CardEntry.startCardEntryActivity(this)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        CardEntry.handleActivityResult(data, object : Callback<CardEntryActivityResult> {
            override fun onResult(result: CardEntryActivityResult) {
                val points = viewModel.currentSelectedPts.value
                if(result.isSuccess()){
                    val cardResult = result.getSuccessValue()
                    val card = cardResult.card
                    val nonce = cardResult.nonce

                    Log.d("Payment", "onResult: $card $nonce")




                    // process nonce

////                    backgroundHandler.DataSource.setCall()
////                    backgroundHandler.Companion.call
////                    backgroundHandler.call = paymentRepo.callPaymentInBackground(nonce, points.toString())
//                    CardEntry.setCardNonceBackgroundHandler(backgroundHandler)

                    Log.d("Points", "onResult: $points")

//                    CoroutineScope(Dispatchers.IO).launch {
////                        paymentRepo.callPaymentInBackground(nonce, points.toString())
//                        val backgroundHandler = CardEntryBackgroundHandler(points.toString())
//                        backgroundHandler.handleEnteredCardInBackground(cardResult)
//                    }

                    if(result.isSuccess()){
                        showSuccessCharge()
                    }else{
                        showError()
                    }

                }else if(result.isCanceled()){
                    Toast.makeText(applicationContext, "Canceled", Toast.LENGTH_LONG).show()

                }
            }
        })
    }



    fun showSuccessCharge(){
        showDialog(R.string.title_order_successful, getString(R.string.msg_order_successful))
    }

    fun showError(){
        showDialog(R.string.title_order_unsuccessful, getString(R.string.msg_order_unsuccessful))
    }

    fun showDialog(strResId : Int, msg : CharSequence){
        AlertDialog.Builder(this)
            .setTitle(strResId)
            .setMessage(msg)
            .setPositiveButton(android.R.string.ok, null)
            .show()
    }

//    override fun cancel() {
//        call.cancel()
//    }

    //
//
    //    override fun enqueue(callback: Callback<ChargeResult>) {
//        call.enqueue(object : retrofit2.Callback<Void> {
//            override fun onResponse(
//                call: retrofit2.Call<Void>,
//                response: Response<Void>
//            ) {
//                callback.onResult(responseToResult(response))
//            }
//
//            override fun onFailure(call: retrofit2.Call<Void>, throwable: Throwable) {
//                if (throwable is IOException) {
//                    callback.onResult(ChargeResult.networkError())
//                } else {
//                    throw RuntimeException("Unexpected exception", throwable)
//                }
//            }
//        })
//    }


//    override fun execute(): ChargeResult {
//        val response : Response<Void>
//        try {
//            response = call.execute()
//        }catch (e : IOException){
//            return ChargeResult.networkError()
//        }
//        return responseToResult(response)
//    }
//
//    override fun isCanceled(): Boolean {
//        return call.isCanceled
//    }
//
//    override fun isExecuted(): Boolean {
//        return call.isExecuted
//    }

//    private fun responseToResult(response : Response<Void>) : ChargeResult{
//        if(response.isSuccessful){
//            return ChargeResult.success()
//        }
//
//        return try {
//            val errorBody = response.errorBody()
//            val errorResponse: SquareAPI.ChargeErrorResponse = paymentRepo.errorConverter.convert(errorBody)!!
//            ChargeResult.error(errorResponse.errorMessage)
//        } catch (exception: IOException) {
//            if (BuildConfig.DEBUG) {
//                Log.d(
//                    "ChargeCall", "Error while parsing error response: $response",
//                    exception
//                )
//            }
//            ChargeResult.networkError()
//        }
//
//    }
}