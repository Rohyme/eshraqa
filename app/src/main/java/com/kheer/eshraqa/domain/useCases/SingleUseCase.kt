package com.kheer.eshraqa.domain.useCases

import  io.reactivex.BackpressureStrategy
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

// Created by Rohyme on 10/23/2018.
abstract class SingleUseCase<T, in params> {

    private var publisher: PublishSubject<Long> = PublishSubject.create()

    private val disposables = CompositeDisposable()
    private var isSubscribed = false

    protected abstract fun buildUseCaseObservable(params: params? = null): Single<T>


    open fun execute(singleObserver: SingleObserver<T>, params: params? = null) {
        val single = this.buildUseCaseObservable(params)
                .doOnError {
                    isSubscribed = false
                    singleObserver.onError(it)
                }
                .doOnSubscribe {
                    isSubscribed = true
                    singleObserver.onSubscribe(it)
                }.retryWhen {
                    isSubscribed = false
                    return@retryWhen publisher.toFlowable(BackpressureStrategy.LATEST)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) as Single<T>
        addDisposable(single.subscribe({
            singleObserver.onSuccess(it)
            isSubscribed = false
        }, {
            singleObserver.onError(it)
            isSubscribed = false
        }))
    }

    fun retry() {
        publisher.onNext(System.currentTimeMillis())
    }

    fun unSubscribe() {
        if (!disposables.isDisposed) disposables.dispose()
    }

    fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    fun isSubscribe(): Boolean {
        return isSubscribed
    }

}