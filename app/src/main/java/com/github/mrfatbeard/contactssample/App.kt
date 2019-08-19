package com.github.mrfatbeard.contactssample

import android.app.Application
import com.github.mrfatbeard.contactssample.di.Injector

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        Injector.createComponent(this)
    }
}