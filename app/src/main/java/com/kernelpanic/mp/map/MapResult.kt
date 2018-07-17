package com.kernelpanic.mp.map

import com.kernelpanic.mp.model.base.BaseResult

/**
 * Created by Przemysław Petka on 7/7/2018.
 */
sealed class MapResult : BaseResult {

    sealed class LoadMapResult : MapResult() {
        data class Success(val result: String) : LoadMapResult()
        data class Failure(val error: Throwable) : LoadMapResult()
        object InFlight : LoadMapResult()
    }
}
