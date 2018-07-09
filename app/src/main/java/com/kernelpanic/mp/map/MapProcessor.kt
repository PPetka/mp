package com.kernelpanic.mp.map

import android.util.Log
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.Consumer
import java.util.concurrent.TimeUnit

/**
 * Created by Przemysław Petka on 7/7/2018.
 */
class MapProcessor {

    var actionProcessor: ObservableTransformer<MapAction, MapResult> = ObservableTransformer { action ->
        action.flatMap {
            getItemFromServer
                    .map { item -> MapResult.Success(item) }
                    .cast(MapResult::class.java)
                    .delay(5, TimeUnit.SECONDS)
                    .startWith(MapResult.InFlight)
                    .doOnNext(Consumer { Log.e("PROCESSING", it.toString()) })

        }

    }

    val getItemFromServer: Observable<String> = Observable.just("ItemFromServer")
}
