package com.kernelpanic.mp.utils

import com.kernelpanic.mp.model.LatLang
import java.util.*

/**
 * Created by Przemysław Petka on 6/6/2018.
 */
object LatLangGenerator {

    fun getRandomLatLang(): LatLang{
        return LatLang(RandGen.getRandInRange(0.0,50.00),RandGen.getRandInRange(0.0,50.00))
    }
}