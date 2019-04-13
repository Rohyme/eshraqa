package com.kheer.eshraqa.presentation.ui.screens.mainScreen.eshraqatMainListScreen

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class EshraqatMainListViewModel @Inject constructor() :ViewModel() {
  var mainEshraqatList: ArrayList<String> = ArrayList()
  fun setList() {
    for (i in 0..30){
      mainEshraqatList.add("Eshraqa $i")
    }
  }
}