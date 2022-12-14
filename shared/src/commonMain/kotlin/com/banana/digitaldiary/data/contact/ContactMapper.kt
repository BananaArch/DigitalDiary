package com.banana.digitaldiary.domain.data.contact

import com.banana.digitaldiary.domain.contact.Contact
import com.banana.digitaldiary.domain.note.Note
import database.ContactEntity
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun ContactEntity.toContact(): Contact {
    return Contact(
        id = id,
        name = name,
        phone = phone,
        email = email,
        colorHex = colorHex
    )
}
