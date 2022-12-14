package com.banana.digitaldiary.android.contact.contact_list

import com.banana.digitaldiary.domain.contact.Contact

data class ContactListState (
    val contacts: List<Contact> = emptyList(),
    val searchText: String = "",
    val isSearchActive: Boolean = false
)

