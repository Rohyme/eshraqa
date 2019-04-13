package com.kheer.eshraqa.presentation.di.components

import com.kheer.eshraqa.presentation.di.modules.ApplicationModule
import com.kheer.eshraqa.presentation.myApp.MyApplication
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(ApplicationModule::class)])
interface ApplicationComponent {
  fun injectApp(myApp: MyApplication)
  @Component.Builder
  interface Builder {
    fun builder(): ApplicationComponent
    @BindsInstance
    fun applicationContext(appContext: MyApplication): Builder
  }
}