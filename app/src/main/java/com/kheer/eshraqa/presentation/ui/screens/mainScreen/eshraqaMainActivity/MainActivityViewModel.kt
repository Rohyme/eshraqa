package com.kheer.eshraqa.presentation.ui.screens.mainScreen.eshraqaMainActivity

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MainActivityViewModel @Inject constructor() : ViewModel() {
  var  mainEshraqatList :ArrayList<String> = ArrayList()

   fun setList() {
    for (i in 0..30){
      mainEshraqatList.add("Eshraqa $i")
    }
  }
}