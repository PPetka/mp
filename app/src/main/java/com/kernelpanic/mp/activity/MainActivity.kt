package com.kernelpanic.mp.activity

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.kernelpanic.mp.R
import com.kernelpanic.mp.utils.ViewModelFactory
import com.kernelpanic.mp.viewmodel.MainActivityViewModel
import com.google.android.gms.maps.MapFragment
import com.kernelpanic.mp.map.MapActivityIntent
import com.kernelpanic.mp.map.MapUiViewState
import com.kernelpanic.mp.model.base.BaseView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


class MainActivity : AppCompatActivity(), BaseView<MapActivityIntent, MapUiViewState>, OnMapReadyCallback {
    var maps: GoogleMap? = null

    var loadMarkersPublisher: PublishSubject<MapActivityIntent.LoadMarkersIntent> = PublishSubject.create()

    override fun onMapReady(maps: GoogleMap?) {
        this.maps = maps
        loadMarkersPublisher.onNext(MapActivityIntent.LoadMarkersIntent)
    }

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragment = fragmentManager.findFragmentById(R.id.map) as MapFragment
        mapFragment.getMapAsync(this)

        viewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance(this))
                .get(MainActivityViewModel::class.java)


    }

    override fun render(state: MapUiViewState) {
        when (state) {
            is MapUiViewState.InitialState -> {
                Toast.makeText(this, "Initial State", Toast.LENGTH_SHORT).show()
            }
            is MapUiViewState.MapLoadedState -> {
                Log.e("TAG", maps?.toString())
            }
        }
    }

    override fun intents(): Observable<MapActivityIntent> {
        return Observable.merge(initialIntent(), loadMarkersPublisher)
    }

    private fun initialIntent(): Observable<MapActivityIntent.InitialIntent> {
        return Observable.just(MapActivityIntent.InitialIntent)
    }
}
