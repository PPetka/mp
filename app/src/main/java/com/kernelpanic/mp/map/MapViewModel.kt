package com.kernelpanic.mp.map

import android.arch.lifecycle.ViewModel
import android.util.Log
import com.kernelpanic.mp.map.*
import com.kernelpanic.mp.model.base.BaseViewModel
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import com.kernelpanic.mp.map.MapResult.LoadMapResult

/**
 * Created by Przemysław Petka on 6/6/2018.
 */
class MapViewModel(val processor: MapProcessor) : ViewModel(), BaseViewModel<MapActivityIntent, MapUiViewState> {
    private var intentsSubject: PublishSubject<MapActivityIntent> = PublishSubject.create()

    override fun processIntents(intents: Observable<MapActivityIntent>) {
        intents.subscribe(intentsSubject)
    }

    override fun provideViewStates(): Observable<MapUiViewState> {
        return intentsSubject
                .distinctUntilChanged()
                .map(this::actionFromIntent)
                .compose(processor.actionProcessor)
                .scan<MapUiViewState>(MapUiViewState.InitialState, reducer)
                .replay(1)
                .autoConnect(0)
    }

    fun actionFromIntent(intent: MapActivityIntent): MapAction {
        return when (intent) {
            is MapActivityIntent.InitialIntent -> MapAction.PrepareScreen
            is MapActivityIntent.LoadMarkersIntent -> MapAction.Load
        }
    }

    private val reducer: BiFunction<MapUiViewState, MapResult, MapUiViewState> =
            BiFunction { previousState, result ->
                Log.e("REDUCING", result.toString())
                when (result) {
                    is LoadMapResult -> when (result) {
                        is LoadMapResult.InFlight -> MapUiViewState.InProgress
                        is LoadMapResult.Success -> MapUiViewState.MapLoadedState
                        is LoadMapResult.Failure -> MapUiViewState.Failed
                    }
                }
            }
}