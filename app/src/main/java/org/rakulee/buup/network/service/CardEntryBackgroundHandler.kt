package org.rakulee.buup.network.service

import android.content.res.Resources
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.rakulee.buup.model.ChargeResult
import org.rakulee.buup.module.NetworkModule

import org.rakulee.buup.repo.PaymentRepo
import org.rakulee.buup.viewmodel.PaymentViewModel
import retrofit2.Response
import retrofit2.Retrofit
import sqip.CardDetails
import sqip.CardEntryActivityCommand
import sqip.CardNonceBackgroundHandler
import javax.inject.Inject
import javax.inject.Singleton


class CardEntryBackgroundHandler() : CardNonceBackgroundHandler {
    lateinit var retrofit : Retrofit
    lateinit var squareAPI: SquareAPI
    lateinit var paymentRepo: PaymentRepo
    lateinit var paymentViewModel: PaymentViewModel
    lateinit var call : retrofit2.Call<ChargeResult>

    companion object {
        @JvmStatic
        lateinit var points : String
    }

    override fun handleEnteredCardInBackground(cardDetails: CardDetails): CardEntryActivityCommand {
        // process nonce
        retrofit = NetworkModule.providesRetrofit()
        squareAPI = NetworkModule.providesSquareApiService(retrofit)
        paymentRepo = NetworkModule.providesSquareRepository(squareAPI)
        

        points = (points.toInt() / 100).toString()

        Log.d("Points", "handleEnteredCardInBackground: $points")


        call = paymentRepo.callPaymentInBackground(cardDetails.nonce, points)

        val result = call.execute()
        if(result.isSuccessful){
            return CardEntryActivityCommand.Finish()
        }else{
            return CardEntryActivityCommand.ShowError("There was an error processing the payment")
        }

    }
}