package com.kheer.eshraqa.presentation.appUtils

/**
 *
 * @Auther Rohyme
 */
sealed class StateView {
    data class Success<out T>(val data: T) : StateView()
    data class SuccessWithState<out T>(val data: T, val statusCode: Int) : StateView()
    data class Error(val error: Throwable?) : StateView()
    data class ErrorWithState(val error: Throwable?, val statusCode: Int) : StateView()
    object Empty : StateView()
    data class ErrorWithData<out T>(val error: Throwable?, val payload: T) : StateView()
    object Loading : StateView()
}