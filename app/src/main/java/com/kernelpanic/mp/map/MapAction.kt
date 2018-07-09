package com.kernelpanic.mp.map

/**
 * Created by Przemysław Petka on 7/5/2018.
 */
sealed class MapAction {
    object Load : MapAction()
    object PrepareScreen : MapAction()
}