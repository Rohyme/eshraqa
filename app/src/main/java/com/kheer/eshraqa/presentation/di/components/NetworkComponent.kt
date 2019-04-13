package com.kheer.eshraqa.presentation.di.components

import com.kheer.eshraqa.presentation.di.modules.ApplicationModule
import com.kheer.eshraqa.presentation.di.modules.NetworkModule
import com.kheer.eshraqa.presentation.di.modules.SharedPreferenceModule
import com.kheer.eshraqa.presentation.myApp.MyApplication
import com.kheer.eshraqa.presentation.ui.base.BaseActivityWithInjector
import com.kheer.eshraqa.presentation.ui.base.BaseFragmentWithInjector
import com.kheer.eshraqa.presentation.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ApplicationModule::class, NetworkModule::class, ViewModelModule::class, SharedPreferenceModule::class]
)
interface NetworkComponent {
  fun inject(activity: BaseActivityWithInjector)
  fun inject(fragment: BaseFragmentWithInjector)

  @Component.Builder
  interface NetworkBuilder {
    fun builder(): NetworkComponent
    @BindsInstance
    fun application(app: MyApplication): NetworkBuilder
    @BindsInstance
    fun baseUrl(baseUrl: String): NetworkBuilder
  }
}