package com.kheer.eshraqa.presentation.di.modules

import android.content.Context
import com.kheer.eshraqa.presentation.di.qualifiers.ForApplication
import com.kheer.eshraqa.data.service.ApiService
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

  @Singleton
  @Provides
  fun providesHttpCache(@ForApplication appContext: Context): Cache? {
    val cacheSize = 10 * 1024 * 1024
    var cache: Cache? = null
    try {
      val myDir = File(appContext.cacheDir, "response")
      myDir.mkdir()
      cache = Cache(myDir, cacheSize.toLong())
    } catch (e: Exception) {
      e.printStackTrace()
    }

    return cache
  }

  @Singleton
  @Provides
  fun providesGson(): Gson {
    val gsonBuilder = GsonBuilder()
    gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
    return gsonBuilder.create()
  }

  @Singleton
  @Provides
  fun provideOkhttpClient(cache: Cache?): OkHttpClient {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    return OkHttpClient.Builder()
//                .cache(cache)
        .writeTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .build()
  }


  @Singleton
  @Provides
  fun providesRetrofit(gson: Gson, okHttpClient: OkHttpClient, baseUrl: String): Retrofit {
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .build()
  }

  @Singleton
  @Provides
  fun providesApiService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
  }

}