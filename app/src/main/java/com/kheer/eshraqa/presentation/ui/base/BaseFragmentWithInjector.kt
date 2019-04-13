package com.kheer.eshraqa.presentation.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import com.kheer.eshraqa.presentation.di.modules.ViewModelFactory
import com.kheer.eshraqa.presentation.myApp.MyApplication
import io.reactivex.disposables.Disposable
import javax.inject.Inject

abstract  class BaseFragmentWithInjector : androidx.fragment.app.Fragment(){
  @Inject
  lateinit var factory: ViewModelFactory
  lateinit var vm: ViewModel
  lateinit var disposal: Disposable

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    (activity?.application as MyApplication).networkComponent.inject(this)
    vm = ViewModelProviders.of(this, factory)[getActivityVM()]
    if (isRetained()) retainInstance=true
  }



  abstract fun getActivityVM(): Class<out ViewModel>
  open  fun  isRetained():Boolean{
    return false
  }

}