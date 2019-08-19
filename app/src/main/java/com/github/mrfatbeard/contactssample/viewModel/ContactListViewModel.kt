package com.github.mrfatbeard.contactssample.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mrfatbeard.contactssample.data.ContactVO
import com.github.mrfatbeard.contactssample.data.ContactsRepository
import com.github.mrfatbeard.contactssample.di.Injector
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ContactListViewModel : ViewModel(), CoroutineScope {
    private val repo: ContactsRepository = Injector.getComponent().getContactsRepository()
    private val contactListLiveData = MutableLiveData<List<ContactVO>>()
    private val loadingLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()

    fun loadContactList(
        searchQuery: String? = null,
        coroutineContext: CoroutineContext = Dispatchers.IO
    ) {
        loadingLiveData.value = true
        launch(coroutineContext) {
            val result = repo.listContacts(searchQuery)
            withContext(Dispatchers.Main) {
                contactListLiveData.value = result
                loadingLiveData.value = false
            }
        }
    }

    fun getContactListLiveData(): LiveData<List<ContactVO>> {
        return contactListLiveData
    }

    fun getLoadingLiveData(): LiveData<Boolean> {
        return loadingLiveData
    }
}