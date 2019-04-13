package com.kheer.eshraqa.presentation.ui.screens.mainScreen.eshraqaMainActivity

import android.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import android.os.Bundle
import com.kheer.eshraqa.R
import com.kheer.eshraqa.presentation.ui.base.BaseActivityWithInjector
import com.kheer.eshraqa.presentation.ui.screens.mainScreen.eshraqatMainListScreen.EshraqatMainListFragment



class MainActivity : BaseActivityWithInjector() {
  lateinit var viewModel : MainActivityViewModel
  override fun getActivityVM(): Class<out ViewModel> {
 return MainActivityViewModel::class.java
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    viewModel = vm as MainActivityViewModel
    val eshraqatMainListFragment = supportFragmentManager.findFragmentByTag(EshraqatMainListFragment.TAG)?: EshraqatMainListFragment()
    val trans =  supportFragmentManager.beginTransaction()
        trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        trans.addToBackStack(null).replace(R.id.fragmentsContainer,eshraqatMainListFragment,EshraqatMainListFragment.TAG).commit()
  }


}
