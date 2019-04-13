package com.kheer.eshraqa.presentation.myApp

import android.app.Application
import android.content.IntentFilter
import android.net.ConnectivityManager
import com.kheer.eshraqa.presentation.di.components.ApplicationComponent
import com.kheer.eshraqa.presentation.di.components.NetworkComponent
import com.kheer.eshraqa.presentation.appUtils.Constants
import com.kheer.eshraqa.presentation.appUtils.networkUtils.ConnectivityReciever
import com.kheer.eshraqa.presentation.di.components.DaggerApplicationComponent
import com.kheer.eshraqa.presentation.di.components.DaggerNetworkComponent
import com.tripl3dev.prettystates.StatesConfigFactory

class MyApplication : Application(){
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

  StatesConfigFactory.intialize().initDefaultViews()
}
  override fun onTerminate() {
    super.onTerminate()
    networkDetector.abortBroadcast()
  }
}