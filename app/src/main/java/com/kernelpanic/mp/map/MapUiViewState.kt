package com.kernelpanic.mp.map

import com.kernelpanic.mp.model.base.BaseViewState

/**
 * Created by Przemysław Petka on 6/27/2018.
 */
sealed class MapUiViewState : BaseViewState {

    object InitialState : MapUiViewState()

    object MapLoadedState : MapUiViewState()

    object InProgress : MapUiViewState()

    object Failed : MapUiViewState()

}