package com.github.mrfatbeard.contactssample.ui

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.epoxy.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.mrfatbeard.contactssample.R
import com.github.mrfatbeard.contactssample.data.ContactVO

@EpoxyModelClass(layout = R.layout.list_item_contact)
abstract class ContactModel : EpoxyModelWithHolder<ContactHolder>() {
    @EpoxyAttribute
    lateinit var contact: ContactVO

    override fun bind(holder: ContactHolder) {
        with(holder) {
            nameTextView.text = contact.name
            Glide.with(profilePicImageView)
                .load(contact.photoUri)
                .apply(RequestOptions().circleCrop().fallback(R.drawable.ic_person_black_48dp))
                .into(profilePicImageView)
        }
    }
}

class ContactHolder : EpoxyHolder() {
    lateinit var nameTextView: TextView
    lateinit var profilePicImageView: ImageView

    override fun bindView(itemView: View) {
        nameTextView = itemView.findViewById(R.id.nameTextView)
        profilePicImageView = itemView.findViewById(R.id.profilePicImageView)
    }
}