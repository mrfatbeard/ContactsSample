package com.github.mrfatbeard.contactssample.di

import com.github.mrfatbeard.contactssample.data.ContactsRepository
import dagger.Component
import javax.inject.Singleton

@Component(modules = [ContextModule::class, RepositoryModule::class])
@Singleton
interface AppComponent {
    fun getContactsRepository(): ContactsRepository
}