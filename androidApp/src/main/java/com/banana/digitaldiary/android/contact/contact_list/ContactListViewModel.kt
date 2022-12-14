package com.banana.digitaldiary.android.contact.contact_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.banana.digitaldiary.domain.contact.Contact
import com.banana.digitaldiary.domain.contact.ContactDataSource
import com.banana.digitaldiary.data.contact.SearchContacts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactListViewModel @Inject constructor(

    private val contactDataSource: ContactDataSource,
    private val savedStateHandle: SavedStateHandle

): ViewModel() {

    private val searchContacts = SearchContacts()

    private val contacts = savedStateHandle.getStateFlow("contacts", emptyList<Contact>())
    private val searchText = savedStateHandle.getStateFlow("searchText", "")
    private val isSearchActive = savedStateHandle.getStateFlow("isSearchActive", false)

    val state = combine(contacts, searchText, isSearchActive) {
        contacts, searchText, isSearchActive ->
        ContactListState(

            contacts = searchContacts.search(contacts, searchText),
            searchText = searchText,
            isSearchActive = isSearchActive

        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ContactListState())



    fun loadContacts() {
        viewModelScope.launch {
            savedStateHandle["contacts"] = contactDataSource.getAllContacts()
        }
    }

    fun onSearchTextChange(text: String) {
        savedStateHandle["searchText"] = text
    }

    fun onToggleSearch() {
        savedStateHandle["isSearchActive"] = !isSearchActive.value
        if (!isSearchActive.value) {
            savedStateHandle["searchText"] = ""
        }
    }

    fun deleteContactById(id: Long) {
        viewModelScope.launch {
            contactDataSource.deleteContactById(id)
            loadContacts()
        }
    }
}