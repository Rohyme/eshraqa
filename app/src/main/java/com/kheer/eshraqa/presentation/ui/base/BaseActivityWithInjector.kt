package com.kheer.eshraqa.presentation.ui.base

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.multidex.MultiDex
import com.kheer.eshraqa.presentation.appUtils.Constants
import com.kheer.eshraqa.presentation.appUtils.networkUtils.NetworkUtils
import com.kheer.eshraqa.presentation.myApp.MyApplication
import com.kheer.eshraqa.presentation.di.modules.ViewModelFactory
import com.smartit.pnuemp.presentation.appUtils.languageUtils.LocalizationUtils
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import javax.inject.Inject

 abstract class BaseActivityWithInjector  : AppCompatActivity() , NetworkUtils.ConnectionStatusCB{
  @Inject
  lateinit var factory: ViewModelFactory

  lateinit var vm: ViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    (application as MyApplication).networkComponent.inject(this)
    vm = ViewModelProviders.of(this, factory)[getActivityVM()]
    onConnectionChanged()

  }

     override fun attachBaseContext(newBase: Context?) {
         val languageContext = LocalizationUtils().setLocale(newBase!!, "ar")
         MultiDex.install(this)
         super.attachBaseContext(CalligraphyContextWrapper.wrap(languageContext))
     }


     abstract fun getActivityVM(): Class<out ViewModel>


   override fun onConnected() {
   }

   override fun onDisconnected() {
   }

   @SuppressLint("CheckResult")
   fun onConnectionChanged() {
     NetworkUtils.getNetworkUtils().getNetworkStatus().subscribe {
       when (it) {
         NetworkUtils.CONNECTED -> {
           onConnected()
         }
         NetworkUtils.DISCONNECTED -> {
           onDisconnected()
         }
       }
     }
   }

 }