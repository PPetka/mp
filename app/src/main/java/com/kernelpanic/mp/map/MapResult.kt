package com.kernelpanic.mp.map

import com.kernelpanic.mp.model.base.BaseResult

/**
 * Created by Przemysław Petka on 7/7/2018.
 */
sealed class MapResult : BaseResult {

    data class Success(val result: String) : MapResult()
    data class Failure(val error: Throwable) : MapResult()
    object InFlight : MapResult()
}
