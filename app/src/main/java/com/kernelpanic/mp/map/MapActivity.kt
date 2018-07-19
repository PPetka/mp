package com.kernelpanic.mp.map

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.kernelpanic.mp.R
import com.kernelpanic.mp.utils.ViewModelFactory
import com.google.android.gms.maps.MapFragment
import com.kernelpanic.mp.model.base.BaseView
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject


class MapActivity : AppCompatActivity(), BaseView<MapActivityIntent, MapUiViewState>, OnMapReadyCallback {
    var maps: GoogleMap? = null

    var loadMarkersPublisher: PublishSubject<MapActivityIntent.LoadMarkersIntent> = PublishSubject.create()

    lateinit var progressBar: ProgressBar
    override fun onMapReady(maps: GoogleMap?) {
        this.maps = maps
        loadMarkersPublisher.onNext(MapActivityIntent.LoadMarkersIntent)
    }

    private lateinit var viewModel: MapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progressBar)
        val mapFragment = fragmentManager.findFragmentById(R.id.map) as MapFragment
        mapFragment.getMapAsync(this)


        viewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance(this))
                .get(MapViewModel::class.java)


        viewModel.provideViewStates()
                .subscribe(Consumer {
                    render(it)
                })

        viewModel.processIntents(intents())
    }

    override fun render(state: MapUiViewState) {
        when (state) {
            is MapUiViewState.InitialState -> {
                Log.e("RENDERING", "InitialState")
                progressBar.visibility = View.GONE
            }
            is MapUiViewState.MapLoadedState -> {
                Log.e("RENDERING", "MapLoadedState")
                progressBar.visibility = View.GONE
            }
            is MapUiViewState.Failed -> {
                Log.e("RENDERING", "Failed")
                progressBar.visibility = View.GONE
            }
            is MapUiViewState.InProgress -> {
                Log.e("RENDERING", "InProgress")
                progressBar.visibility = View.VISIBLE
            }
        }
    }

    override fun intents(): Observable<MapActivityIntent> {
        return Observable.merge(initialIntent(), loadMarkersPublisher).doOnNext(Consumer {
            Log.e("EMITING", it.toString())
        })
    }

    private fun initialIntent(): Observable<MapActivityIntent.InitialIntent> {
        return Observable.just(MapActivityIntent.InitialIntent)
    }
}
