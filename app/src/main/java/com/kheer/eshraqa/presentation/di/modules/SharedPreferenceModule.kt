package com.kheer.eshraqa.presentation.di.modules

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.kheer.eshraqa.presentation.myApp.MyApplication
import com.kheer.eshraqa.presentation.appUtils.Constants
import com.kheer.eshraqa.presentation.appUtils.languageUtils.SharedPreferenceUtil
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedPreferenceModule {
  @Provides
  @Singleton
  fun provideSharedPreference(app: MyApplication): SharedPreferences {
    return app.getSharedPreferences(Constants.SHARED_PREFERENCE, Context.MODE_PRIVATE)
  }

  @Provides
  @Singleton
  fun provideSharedPreferenceUtil(sharedPreference: SharedPreferences,gson :Gson): SharedPreferenceUtil {
    return SharedPreferenceUtil(sharedPreference,gson)
  }
}