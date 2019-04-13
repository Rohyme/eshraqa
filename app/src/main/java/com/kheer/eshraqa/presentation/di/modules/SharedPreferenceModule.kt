package com.kheer.eshraqa.presentation.di.modules

import android.content.Context
import android.content.SharedPreferences
import com.kheer.eshraqa.presentation.myApp.MyApplication
import com.kheer.eshraqa.presentation.appUtils.Constants
import com.kheer.eshraqa.presentation.appUtils.SharedPreferenceUtil
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
  fun provideSharedPreferenceUtil(sharedPreference: SharedPreferences): SharedPreferenceUtil {
    return SharedPreferenceUtil(sharedPreference)
  }
}