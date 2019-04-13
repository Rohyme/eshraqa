package com.kheer.eshraqa.presentation.di.modules

import android.content.Context
import com.kheer.eshraqa.presentation.di.qualifiers.ForApplication
import com.kheer.eshraqa.presentation.myApp.MyApplication
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

  @Provides
  @ForApplication
  fun applicationContext(app: MyApplication): Context {
    return app.applicationContext
  }

}