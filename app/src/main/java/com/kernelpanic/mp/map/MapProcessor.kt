package com.kernelpanic.mp.map

import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.internal.operators.observable.ObservableError
import java.util.concurrent.TimeUnit

/**
 * Created by Przemysław Petka on 7/7/2018.
 */
class MapProcessor {
    var actionProcessor: ObservableTransformer<MapAction, MapResult> = ObservableTransformer { actions ->
        actions.publish { sharedAction ->
            Observable.merge(
                    sharedAction.ofType(MapAction.Load::class.java)
                            .compose(mapItemsProcessor),
                    sharedAction.ofType(MapAction.PrepareScreen::class.java)
                            .compose(prepareScreenProcessor))
                    .mergeWith(
                            sharedAction.filter { v ->
                                v !is MapAction.Load
                                        && v !is MapAction.PrepareScreen
                            }.flatMap { w ->
                                Observable.error<MapResult>(IllegalArgumentException("D"))
                            })
        }
    }

}

var mapItemsProcessor: ObservableTransformer<MapAction, MapResult> = ObservableTransformer { actions ->
    actions.flatMap {
        getItemFromServer
                .map { item -> MapResult.LoadMapResult.Success(item) }
                .cast(MapResult::class.java)
                .onErrorReturn(MapResult.LoadMapResult::Failure)
                .delay(5, TimeUnit.SECONDS)
                .startWith(MapResult.LoadMapResult.InFlight)
                .doOnNext { Log.e("PROCESSING", it.toString()) }

    }
}

var prepareScreenProcessor: ObservableTransformer<MapAction, MapResult> = ObservableTransformer { actions ->
    actions.flatMap {
        prepareScreen
                .map { item -> MapResult.LoadMapResult.Success(item) }
                .cast(MapResult::class.java)
                .onErrorReturn(MapResult.LoadMapResult::Failure)
                .delay(5, TimeUnit.SECONDS)
                .startWith(MapResult.LoadMapResult.InFlight)
                .doOnNext { Log.e("PROCESSING", it.toString()) }

    }
}

//todo temporary values -> create and move to repository
val getItemFromServer: Observable<String> = Observable.just("ItemFromServer")
val prepareScreen: Observable<String> = Observable.just("PrepareScreenItem")


