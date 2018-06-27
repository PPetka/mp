package com.kernelpanic.mp.viewmodel

import android.arch.lifecycle.ViewModel
import com.kernelpanic.mp.SomeProcessor
import com.kernelpanic.mp.map.MapActivityIntent
import com.kernelpanic.mp.map.MapUiViewState
import com.kernelpanic.mp.model.base.BaseViewModel
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by Przemysław Petka on 6/6/2018.
 */
class MainActivityViewModel(processor: SomeProcessor) : ViewModel() , BaseViewModel<MapActivityIntent,MapUiViewState> {
    private var intentsSubject: PublishSubject<MapActivityIntent> = PublishSubject.create()

    override fun processIntents(intents: Observable<MapActivityIntent>) {
        intents.subscribe(intentsSubject)
    }

    override fun provideViewStates(): Observable<MapUiViewState> {

    }
}