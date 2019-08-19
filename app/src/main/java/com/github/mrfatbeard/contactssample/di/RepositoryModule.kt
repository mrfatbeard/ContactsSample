package com.github.mrfatbeard.contactssample.di

import android.content.Context
import com.github.mrfatbeard.contactssample.data.ContactsRepository
import com.github.mrfatbeard.contactssample.data.DefaultContactsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideContactsRepository(context: Context): ContactsRepository =
        DefaultContactsRepository(context)
}
