package com.kheer.eshraqa.presentation.ui.screens.mainScreen.eshraqatMainListScreen

import androidx.lifecycle.ViewModel
import com.kheer.eshraqa.data.service.responseModel.EshraqatResponse
import com.kheer.eshraqa.domain.useCases.AddToFavouriteUseCase
import com.kheer.eshraqa.domain.useCases.GetAllEshraqatUseCase
import com.kheer.eshraqa.presentation.appUtils.SingleEventLiveData
import com.kheer.eshraqa.presentation.appUtils.StateView
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class EshraqatMainListViewModel @Inject constructor(private val getAllEshraqat: GetAllEshraqatUseCase,
                                                    private val addToFavouriteUseCase: AddToFavouriteUseCase) : ViewModel() {
  var mainEshraqatList: ArrayList<EshraqatResponse.Eshraqa> = ArrayList()
  var getEshraqat = SingleEventLiveData<StateView>()

  fun fetchAllEshraqat(isFav: Boolean) {
    getAllEshraqat.execute(object : SingleObserver<ArrayList<EshraqatResponse.Eshraqa>> {
      override fun onSuccess(t: ArrayList<EshraqatResponse.Eshraqa>) {
        if (t.isNullOrEmpty()) {
          getEshraqat.postValue(StateView.Empty)
        } else {
          getEshraqat.postValue(StateView.Success(t))
        }
      }

      override fun onSubscribe(d: Disposable) {
        getEshraqat.postValue(StateView.Loading)
      }

      override fun onError(e: Throwable) {
        getEshraqat.postValue(StateView.Error(e))
      }

    }, isFav)
  }

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