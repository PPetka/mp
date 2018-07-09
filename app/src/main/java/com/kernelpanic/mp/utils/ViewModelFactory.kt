package com.kernelpanic.mp.utils

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.kernelpanic.mp.map.MapProcessor
import com.kernelpanic.mp.viewmodel.MainActivityViewModel

/**
 * Created by Przemysław Petka on 6/13/2018.
 */
class ViewModelFactory private constructor(
        private val applicationContext: Context
)  : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when (modelClass) {
            MainActivityViewModel::class.java -> return MainActivityViewModel(MapProcessor()) as T
        }
        throw IllegalArgumentException("unsuported model class " + modelClass)
    }

    companion object : SingletonHolderSingleArg<ViewModelFactory,Context>(::ViewModelFactory)
}