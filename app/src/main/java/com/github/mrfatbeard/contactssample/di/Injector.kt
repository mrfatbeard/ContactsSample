package com.github.mrfatbeard.contactssample.di

import android.app.Application

object Injector {
    private lateinit var appComponent: AppComponent
    fun createComponent(context: Application) {
        appComponent = DaggerAppComponent.builder()
            .contextModule(ContextModule(context))
            .build()
    }

    fun getComponent(): AppComponent {
        return appComponent
    }
}