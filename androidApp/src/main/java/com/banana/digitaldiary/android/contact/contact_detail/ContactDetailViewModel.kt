package com.banana.digitaldiary.android.contact.contact_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.banana.digitaldiary.domain.contact.Contact
import com.banana.digitaldiary.domain.contact.ContactDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactDetailViewModel @Inject constructor(
    private val contactDataSource: ContactDataSource,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val contactName = savedStateHandle.getStateFlow("contactName", "")
    private val contactPhone = savedStateHandle.getStateFlow("contactPhone", "")
    private val contactEmail = savedStateHandle.getStateFlow("contactEmail", "")
    private val contactColor = savedStateHandle.getStateFlow("contactColor", Contact.generateRandomColor())



    val state = combine(
        contactName,
        contactPhone,
        contactEmail,
        contactColor

    ) {
        contactName, contactPhone, contactEmail, color ->
        ContactDetailState(
            contactName = contactName,
            contactPhone = contactPhone,
            contactEmail = contactEmail,
            contactColor = color
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ContactDetailState())

    private val _hasContactBeenChanged = MutableStateFlow(false)
    val hasContactBeenChanged = _hasContactBeenChanged.asStateFlow()
    private val _hasContactBeenSaved = MutableStateFlow(false)
    val hasContactBeenSaved = _hasContactBeenSaved.asStateFlow()

    private var existingContactId: Long? = null

    init {
        savedStateHandle.get<Long>("contactId")?.let {
            existingContactId ->

            if (existingContactId == -1L)
                return@let

            this.existingContactId = existingContactId
            viewModelScope.launch {
                contactDataSource.getContactById(existingContactId)?.let {
                    contact ->

                    savedStateHandle["contactName"] = contact.name
                    savedStateHandle["contactPhone"] = contact.phone
                    savedStateHandle["contactEmail"] = contact.email
                    savedStateHandle["contactColor"] = contact.colorHex
                }
            }
        }
    }

    fun onContactNameChanged(text: String) {
        savedStateHandle["contactName"] = text
        _hasContactBeenChanged.value = true
    }
    fun onContactEmailChanged(text: String) {
        savedStateHandle["contactEmail"] = text
        _hasContactBeenChanged.value = true
    }
    fun onContactPhoneChanged(text: String) {
        savedStateHandle["contactPhone"] = text
        _hasContactBeenChanged.value = true
    }


    fun saveContact() {
        viewModelScope.launch {

            if (_hasContactBeenChanged.value) {
                contactDataSource.insertContact(
                    Contact(
                        id = existingContactId,
                        name = contactName.value,
                        phone = contactPhone.value,
                        email = contactEmail.value,
                        colorHex = contactColor.value
                    )
                )
            }

            _hasContactBeenSaved.value = true
        }
    }
}