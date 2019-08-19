package com.github.mrfatbeard.contactssample.data

import android.net.Uri

data class ContactVO(
    val id: String,
    val name: String,
    val photoUri: Uri?
)