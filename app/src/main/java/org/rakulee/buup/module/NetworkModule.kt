package org.rakulee.buup.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.rakulee.buup.BuildConfig
import org.rakulee.buup.Configs
import org.rakulee.buup.helper.ApiHelper
import org.rakulee.buup.helper.ApiHelperImpl
import org.rakulee.buup.network.service.*
import org.rakulee.buup.repo.PaymentRepo
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

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class Square

    @Provides
    fun provideBaseUrl() = Configs.BUUP_BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient() = if(BuildConfig.DEBUG){
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }else{
        OkHttpClient.Builder().build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL : String) : Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApiHelper(apiHelper: ApiHelperImpl) : ApiHelper = apiHelper


    @Singleton
    @Provides
    @Square
    fun providesRetrofit() = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Configs.SQUARE_CHARGE_SERVER_URL)
        .build()


//    @Provides
//    @Buup
//    fun providesBuupRetrofit(baseUrl : String) : Retrofit{
//        val retrofit = Retrofit.Builder()
//            .baseUrl(baseUrl)
//            .client(OkHttpClient())
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        return retrofit
//    }


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
    fun provideBuupJobPostingService(retrofit: Retrofit) = retrofit.create(BuupAddJobPostingAPI::class.java)

    @Singleton
    @Provides
    fun provideBuupGetJobPostingByDistanceAPIService(retrofit: Retrofit) = retrofit.create(BuupGetJobPostingByDistanceAPI::class.java)


    @Singleton
    @Provides
    fun provideJobSeekerLoginService(retrofit: Retrofit) = retrofit.create(BuupJobSeekerLoginServiceAPI::class.java)
}