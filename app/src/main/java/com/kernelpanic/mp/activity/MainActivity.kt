package com.kernelpanic.mp.activity

import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.kernelpanic.mp.R
import com.kernelpanic.mp.utils.ViewModelFactory
import com.kernelpanic.mp.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this, ViewModelFactory.getInstance(this))
                .get(MainActivityViewModel::class.java)

    }
}
