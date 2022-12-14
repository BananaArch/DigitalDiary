package com.banana.digitaldiary.data.contact

import com.banana.digitaldiary.database.AppDatabase
import com.banana.digitaldiary.domain.contact.Contact
import com.banana.digitaldiary.domain.contact.ContactDataSource
import com.banana.digitaldiary.domain.data.contact.toContact

class SqlDelightContactDataSource(database: AppDatabase): ContactDataSource {

    private val queries = database.appQueries

    override suspend fun insertContact(contact: Contact) {

        queries.insertContact(contact.id, contact.name, contact.phone, contact.email, contact.colorHex)

    }

    override suspend fun getContactById(id: Long): Contact? {
        return queries
            .getContactById(id)
            .executeAsOneOrNull()
            ?.toContact()
        return null
    }

    override suspend fun getAllContacts(): List<Contact> {
        return queries
            .getAllContacts()
            .executeAsList()
            .map{ it.toContact() }
        return emptyList()
    }

    override suspend fun deleteContactById(id: Long) {
        queries.deleteContactById(id)
    }
}

