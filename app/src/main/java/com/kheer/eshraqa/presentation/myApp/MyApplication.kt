package com.kheer.eshraqa.presentation.myApp

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.multidex.MultiDexApplication
import com.kheer.eshraqa.R
import com.kheer.eshraqa.presentation.appUtils.Constants
import com.kheer.eshraqa.presentation.appUtils.networkUtils.ConnectivityReciever
import com.kheer.eshraqa.presentation.di.components.ApplicationComponent
import com.kheer.eshraqa.presentation.di.components.DaggerApplicationComponent
import com.kheer.eshraqa.presentation.di.components.DaggerNetworkComponent
import com.kheer.eshraqa.presentation.di.components.NetworkComponent
import com.tripl3dev.prettystates.StatesConfigFactory
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

class MyApplication : MultiDexApplication(){
private lateinit var appComponent: ApplicationComponent
lateinit var networkComponent: NetworkComponent
  private lateinit var networkDetector: ConnectivityReciever


  override fun onCreate() {
  super.onCreate()
  appComponent = DaggerApplicationComponent.builder()
      .applicationContext(this)
      .builder()

  networkComponent = DaggerNetworkComponent.builder()
      .application(this)
      .baseUrl(Constants.BASE_URL)
      .builder()

  networkDetector = ConnectivityReciever()
  val intent = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
  registerReceiver(networkDetector, intent)
    CalligraphyConfig.initDefault(
            CalligraphyConfig.Builder()
                    .setDefaultFontPath("fonts/Cairo-Regular.ttf")
                    .setFontAttrId(R.attr.fontPath)
                    .build())

  StatesConfigFactory.intialize().initDefaultViews()
}
  override fun onTerminate() {
    super.onTerminate()
    networkDetector.abortBroadcast()
  }
}