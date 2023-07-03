package com.example.streamwidetechtest.presentation.ui.contact_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.streamwidetechtest.domain.repository.ContactsRepository
import com.example.streamwidetechtest.util.Resource
import com.example.streamwidetechtest.util.UIEvent
import com.example.streamwidetechtest.util.createSqlPattern
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ContactListViewModel @Inject constructor(
    private val repository: ContactsRepository
) : ViewModel() {

    private val _state = mutableStateOf(ContactsListState())
    val state: State<ContactsListState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        fetchContacts()
    }

    fun fetchContacts() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                when (val res = repository.getAllContacts()) {
                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            contactList = res.data ?: emptyList(),
                            isLoading = false
                        )
                    }

                    is Resource.Error -> {
                        _eventFlow.emit(
                            UIEvent.ShowSnackbar(
                                res.message ?: "Unknown error"
                            )
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            contactList = res.data ?: emptyList(),
                            isLoading = true
                        )
                    }
                }
            }
        }
    }

//
//    fun getContactById(contactId: Long) {
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//                when (val res = repository.getContactDetails(contactId)) {
//                    is Resource.Success -> {
//                        Log.i("TAG", res.data!!.toString())
//                    }
//
//                    is Resource.Error -> {
//                        _eventFlow.emit(
//                            UIEvent.ShowSnackbar(
//                                res.message ?: "Unknown error"
//                            )
//                        )
//                    }
//                }
//            }
//        }
//    }

    fun searchByName(name: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                when (val res = repository.selectContactsByName(name.createSqlPattern())) {
                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            contactList = res.data ?: emptyList(),
                            isLoading = false
                        )
                    }

                    is Resource.Error -> {
                        _eventFlow.emit(
                            UIEvent.ShowSnackbar(
                                res.message ?: "Unknown error"
                            )
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            contactList = res.data ?: emptyList(),
                            isLoading = true
                        )
                    }
                }
            }
        }
    }

    fun searchByPhoneNumber(phoneNumber: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                when (val res = repository.selectContactsByPhoneNumber(phoneNumber.createSqlPattern())) {
                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            contactList = res.data ?: emptyList(),
                            isLoading = false
                        )
                    }

                    is Resource.Error -> {
                        _eventFlow.emit(
                            UIEvent.ShowSnackbar(
                                res.message ?: "Unknown error"
                            )
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            contactList = res.data ?: emptyList(),
                            isLoading = true
                        )
                    }
                }
            }
        }
    }

//    fun addNewContact(name: String, phoneNumber: String) {
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//                when (val res = repository.addNewContact(name, phoneNumber)) {
//                    is Resource.Success -> {
//                        Log.i("TAG", "successfully added")
//                    }
//
//                    is Resource.Error -> {
//                        _eventFlow.emit(
//                            UIEvent.ShowSnackbar(
//                                res.message ?: "Unknown error"
//                            )
//                        )
//                    }
//                }
//            }
//        }
//    }
}