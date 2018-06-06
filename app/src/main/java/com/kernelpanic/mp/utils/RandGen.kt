package com.kernelpanic.mp.utils

import java.util.*

/**
 * Created by Przemysław Petka on 6/6/2018.
 */
object RandGen {

    fun getRandInRange(from: Double, to: Double): Double {
        val random = Random()
        return from + (to - from) * random.nextDouble()
    }
}
