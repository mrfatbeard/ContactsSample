package com.github.mrfatbeard.contactssample.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.mrfatbeard.contactssample.R
import com.github.mrfatbeard.contactssample.data.ContactVO
import com.github.mrfatbeard.contactssample.viewModel.ContactListViewModel
import kotlinx.android.synthetic.main.activity_contact_list.*

private const val CONTACTS_PERMISSION_REQUEST_CODE = 666

class ContactListActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private val viewModel: ContactListViewModel
        get() = ViewModelProviders.of(this).get(ContactListViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_list)
        searchView.setOnQueryTextListener(this)
        contactListRecyclerView.itemAnimator = null
        viewModel.getLoadingLiveData().observe(this, Observer { showLoading(it) })
        viewModel.getContactListLiveData().observe(this, Observer { setData(it) })
        checkPermissionAndLoadData()
    }

    private fun setData(data: List<ContactVO>?) {
        contactListRecyclerView.withModels {
            data?.forEach {
                contact {
                    id(it.id)
                    contact(it)
                }
            }
        }
    }

    private fun showLoading(show: Boolean?) {
        if (show == true) {
            loadingProgressBar.visibility = View.VISIBLE
            contactListRecyclerView.visibility = View.INVISIBLE
        } else {
            loadingProgressBar.visibility = View.GONE
            contactListRecyclerView.visibility = View.VISIBLE
        }
    }

    private fun checkPermissionAndLoadData(query: String? = null) {
        val hasContactsPermission = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED
        if (hasContactsPermission) {
            viewModel.loadContactList(query)
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CONTACTS),
                CONTACTS_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode != CONTACTS_PERMISSION_REQUEST_CODE) {
            return
        }
        val permissionGranted = grantResults.size == 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
        if (permissionGranted) {
            viewModel.loadContactList()
        } else {
            showNoPermissionDialog()
        }
    }

    private fun showNoPermissionDialog() {
        AlertDialog.Builder(this)
            .setMessage(R.string.no_contacts_permission)
            .show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        checkPermissionAndLoadData(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        checkPermissionAndLoadData(newText)
        return true
    }
}
