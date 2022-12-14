package com.banana.digitaldiary.domain.contact

interface ContactDataSource {
    suspend fun insertContact(contact: Contact)
    suspend fun getContactById(id: Long): Contact?
    suspend fun getAllContacts(): List<Contact>
    suspend fun deleteContactById(id: Long)
}