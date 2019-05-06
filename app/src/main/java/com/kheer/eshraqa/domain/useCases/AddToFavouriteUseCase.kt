package com.kheer.eshraqa.domain.useCases

import com.kheer.eshraqa.data.repositories.EshraqatRepo
import io.reactivex.Single
import javax.inject.Inject

/**
 *
 * @Auther Rohyme
 */
class AddToFavouriteUseCase @Inject constructor(private val repo :EshraqatRepo): SingleUseCase<Boolean, String>() {
    override fun buildUseCaseObservable(params: String?): Single<Boolean> {
        return  repo.addToFavourite(params!!)
    }
}