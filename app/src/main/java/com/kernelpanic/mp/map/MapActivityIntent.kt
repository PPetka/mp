package com.kernelpanic.mp.map

import com.kernelpanic.mp.model.base.BaseIntent

/**
 * Created by Przemysław Petka on 6/27/2018.
 */
sealed class MapActivityIntent : BaseIntent {

    object LoadMarkersIntent : MapActivityIntent()

    object InitialIntent : MapActivityIntent()

}
