package com.github.mrfatbeard.contactssample.data

import android.content.Context
import android.provider.ContactsContract
import androidx.core.net.toUri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext


interface ContactsRepository {
    suspend fun listContacts(
        searchQuery: String?,
        coroutineContext: CoroutineContext = Dispatchers.IO
    ): List<ContactVO>

}

class DefaultContactsRepository(context: Context) : ContactsRepository {
    private val contentResolver = context.contentResolver

    override suspend fun listContacts(
        searchQuery: String?,
        coroutineContext: CoroutineContext
    ): List<ContactVO> {
        return withContext(coroutineContext) {
            delay(300) // simulate network lag
            val projection =
                arrayOf(
                    ContactsContract.Contacts._ID,
                    ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
                    ContactsContract.Contacts.PHOTO_THUMBNAIL_URI
                )
            val selection = if (searchQuery.isNullOrEmpty()) {
                null
            } else {
                "${ContactsContract.Contacts.DISPLAY_NAME_PRIMARY} like \"%$searchQuery%\""
            }

            val sortOrder = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY

            val cursor =
                contentResolver.query(
                    ContactsContract.Contacts.CONTENT_URI,
                    projection,
                    selection,
                    null,
                    sortOrder
                )
            val result = mutableListOf<ContactVO>()
            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                    val name =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY))
                    val photoUri =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI))
                    result.add(ContactVO(id, name, photoUri?.toUri()))
                }
                cursor.close()
            }
            return@withContext result
        }
    }
}