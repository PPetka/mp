package com.kernelpanic.mp.model.base

import io.reactivex.Observable

/**
 * Created by Przemysław Petka on 6/26/2018.
 */
interface BaseViewModel<I : BaseIntent, S : BaseViewState> {
    fun processIntents(intents: Observable<I>)
    fun provideViewStates(): Observable<S>
}