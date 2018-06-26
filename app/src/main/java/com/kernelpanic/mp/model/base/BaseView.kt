package com.kernelpanic.mp.model.base

import io.reactivex.Observable

/**
 * Created by Przemysław Petka on 6/26/2018.
 */
interface BaseView<I : BaseIntent, in S : BaseViewState> {
    fun intents(): Observable<I>
    fun render(state: S)
}
