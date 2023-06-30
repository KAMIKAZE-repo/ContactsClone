package com.example.streamwidetechtest.presentation.ui.contact_list

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.streamwidetechtest.domain.repository.ContactsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ContactListViewModel @Inject constructor(
    private val repository: ContactsRepository,
    private val context: Application
) : ViewModel() {
    fun fetchContacts() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
//                val list = repository.getAllContacts(context)
//                Log.i("TAG", list.joinToString("|"))
                val contact = repository.getContactDetails(2316)
                Log.i("TAG", contact.toString())
            }
        }
    }
}