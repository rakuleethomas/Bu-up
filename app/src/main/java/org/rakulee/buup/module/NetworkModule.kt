package org.rakulee.buup.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import org.rakulee.buup.Configs
import org.rakulee.buup.network.service.BuupJobPostingAPI
import org.rakulee.buup.network.service.BuupTestAPI
import org.rakulee.buup.network.service.SquareAPI
import org.rakulee.buup.repo.PaymentRepo
import org.rakulee.buup.viewmodel.PaymentViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class Buup

    @Singleton
    @Provides
    fun providesRetrofit() = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Configs.SQUARE_CHARGE_SERVER_URL)
        .build()


    @Provides
    @Buup
    fun providesBuupRetrofit(baseUrl : String) : Retrofit{
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit
    }


    @Singleton
    @Provides
    fun providesSquareApiService(retrofit: Retrofit) = retrofit.create(SquareAPI::class.java)

    @Singleton
    @Provides
    fun providesSquareRepository(apiService : SquareAPI) = PaymentRepo(apiService, providesRetrofit())


    @Singleton
    @Provides
    fun providesBuupTestAPIService(retrofit: Retrofit) = retrofit.create(BuupTestAPI::class.java)

    @Singleton
    @Provides
    fun provideBuupJobPostingService(retrofit: Retrofit) = retrofit.create(BuupJobPostingAPI::class.java)
}