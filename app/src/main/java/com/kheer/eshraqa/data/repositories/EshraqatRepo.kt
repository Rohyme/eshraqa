package com.kheer.eshraqa.data.repositories

import com.kheer.eshraqa.data.service.ApiService
import com.kheer.eshraqa.data.service.responseModel.EshraqatResponse
import com.kheer.eshraqa.presentation.appUtils.languageUtils.SharedPreferenceUtil
import io.reactivex.Single
import javax.inject.Inject

/**
 *
 * @Auther Rohyme
 */

class EshraqatRepo @Inject constructor(private val apiService :ApiService,
                                       private val pref : SharedPreferenceUtil){
    private val auth = "Basic ZXNocmFxYTp1SmVpQTJRZ0ZPb2s="
    fun getAllEshraqat(isFav: Boolean):Single<ArrayList<EshraqatResponse.Eshraqa>>{
        return  apiService.getEshraqat(auth).map {
            var list = it.data
            list.map {eshraqat ->
                eshraqat.isFavourite =pref.getFavsList().contains(eshraqat.id)
            }
            if (isFav)
                list = list.filter {eshraqa ->
                    eshraqa.isFavourite
                }
            ArrayList(list)
        }
    }

    fun addToFavourite(eshraqaId: String):Single<Boolean>{
        return Single.create {
            pref.putToFav(eshraqaId)
            it.onSuccess(true)
        }
    }
}