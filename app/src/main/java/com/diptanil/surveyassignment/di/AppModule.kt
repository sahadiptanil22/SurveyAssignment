package com.diptanil.surveyassignment.di


import android.content.Context
import android.util.Log
import androidx.room.Room
import com.google.gson.GsonBuilder
//import com.diptanil.surveyassignment.BuildConfig
import com.diptanil.surveyassignment.data.dao.SurveyDao
import com.diptanil.surveyassignment.data.database.SurveyDb
import com.diptanil.surveyassignment.utils.BASE_URL
import com.diptanil.surveyassignment.utils.DB_NAME
import com.diptanil.surveyassignment.data.remote.SurveyApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideSurveyDao(appDatabase: SurveyDb): SurveyDao {
        return appDatabase.surveyDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): SurveyDb {
        return Room.databaseBuilder(
            appContext,
            SurveyDb::class.java,
            DB_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideSurveyApiService(retrofit: Retrofit): SurveyApiService =
        retrofit.create(SurveyApiService::class.java)

    @Singleton
    @Provides
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = run {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val request = chain.request()
            Log.i(
                "Sending request to %s",
                request.url.toString()
            )

            chain.proceed(request)
        }
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(interceptor)


        httpClient.connectTimeout(30, TimeUnit.SECONDS)
        httpClient.readTimeout(30, TimeUnit.SECONDS)
        httpClient.build()
    }
}