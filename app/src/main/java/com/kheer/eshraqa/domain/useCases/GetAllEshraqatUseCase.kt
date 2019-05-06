package com.kheer.eshraqa.domain.useCases

import com.kheer.eshraqa.data.repositories.EshraqatRepo
import com.kheer.eshraqa.data.service.responseModel.EshraqatResponse
import io.reactivex.Single
import javax.inject.Inject

/**
 *
 * @Auther Rohyme
 */
class GetAllEshraqatUseCase @Inject constructor(private val repo: EshraqatRepo)  : SingleUseCase<ArrayList<EshraqatResponse.Eshraqa>, Boolean>() {

    override fun buildUseCaseObservable(params: Boolean?): Single<ArrayList<EshraqatResponse.Eshraqa>> {
        return repo.getAllEshraqat(params!!)
    }
}