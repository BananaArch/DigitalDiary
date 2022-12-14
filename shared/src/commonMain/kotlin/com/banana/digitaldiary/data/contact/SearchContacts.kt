package com.banana.digitaldiary.data.contact

import com.banana.digitaldiary.domain.contact.Contact
import com.banana.digitaldiary.domain.note.Note
import com.banana.digitaldiary.domain.time.DateTimeUtil

class SearchContacts {

    fun search(contacts: List<Contact>, query: String): List<Contact> {
        if (query.isBlank()) {
            return contacts.sortedBy{ it.name }
        }
        return contacts.filter {
            it.name.trim().lowercase().contains(query.lowercase()) ||
            it.phone.trim().lowercase().contains(query.lowercase()) ||
            it.email.trim().lowercase().contains(query.lowercase())


        }.sortedBy { it.name }
    }

}