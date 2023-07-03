package com.example.streamwidetechtest.presentation.ui.contact_list

import android.util.Log
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
        startLoading()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                when (val res = repository.getAllContacts()) {
                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            contactList = res.data ?: emptyList(),
                            isLoading = false
                        )
                        Log.i("TAG", res.data!!.joinToString("|"))
                    }

                    is Resource.Error -> {
                        _eventFlow.emit(
                            UIEvent.ShowSnackbar(
                                res.message ?: "Unknown error"
                            )
                        )
                    }
                }
            }
        }
    }

    fun searchByName(name: String) {
        startLoading()
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
                }
            }
        }
    }

    fun searchByPhoneNumber(phoneNumber: String) {
        startLoading()
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
                }
            }
        }
    }

    private fun startLoading(){
        _state.value = state.value.copy(
            contactList = emptyList(),
            isLoading = true
        )
    }
}