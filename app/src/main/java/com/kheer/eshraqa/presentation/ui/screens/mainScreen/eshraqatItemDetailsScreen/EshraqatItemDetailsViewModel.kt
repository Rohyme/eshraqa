package com.kheer.eshraqa.presentation.ui.screens.mainScreen.eshraqatItemDetailsScreen

import androidx.lifecycle.ViewModel
import com.kheer.eshraqa.data.service.responseModel.EshraqatResponse
import com.kheer.eshraqa.domain.useCases.AddToFavouriteUseCase
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class EshraqatItemDetailsViewModel @Inject constructor(private val addToFavouriteUseCase: AddToFavouriteUseCase) : ViewModel() {
  var mainEshraqatList: ArrayList<EshraqatResponse.Eshraqa> = ArrayList()

  fun addToFav(id: String) {
    addToFavouriteUseCase.execute(object :SingleObserver<Boolean>{
      override fun onSuccess(t: Boolean) {
      }

      override fun onSubscribe(d: Disposable) {
      }

      override fun onError(e: Throwable) {
      }

    },id)
  }
}