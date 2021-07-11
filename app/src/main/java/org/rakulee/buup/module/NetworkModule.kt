package org.rakulee.buup.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.rakulee.buup.Configs
import org.rakulee.buup.network.service.SquareAPI
import org.rakulee.buup.repo.PaymentRepo
import org.rakulee.buup.viewmodel.PaymentViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit() = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Configs.SQUARE_CHARGE_SERVER_URL)
        .build()


    @Singleton
    @Provides
    fun providesSquareApiService(retrofit: Retrofit) = retrofit.create(SquareAPI::class.java)

    @Singleton
    @Provides
    fun providesSquareRepository(apiService : SquareAPI) = PaymentRepo(apiService, providesRetrofit())
}