package com.kheer.eshraqa.data.service

import com.kheer.eshraqa.data.service.responseModel.EshraqatResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {

    @GET("controller.php?target=getall")
    fun getEshraqat(@Header("Authorization") auth:String): Single<EshraqatResponse>
}