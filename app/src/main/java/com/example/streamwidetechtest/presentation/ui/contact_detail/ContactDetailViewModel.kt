package com.example.streamwidetechtest.presentation.ui.contact_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.streamwidetechtest.domain.repository.ContactsRepository
import com.example.streamwidetechtest.presentation.ui.contact_list.ContactsListState
import com.example.streamwidetechtest.util.Resource
import com.example.streamwidetechtest.util.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ContactDetailViewModel @Inject constructor(
    private val repository: ContactsRepository
) : ViewModel() {

    private val _state = mutableStateOf(ContactDetailsState())
    val state: State<ContactDetailsState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun fetchContactDetails(contactId: Long) {
        _state.value = _state.value.copy(
            loading = true
        )
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                when (val res = repository.getContactDetails(contactId)) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            data = res.data,
                            loading = true
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

}