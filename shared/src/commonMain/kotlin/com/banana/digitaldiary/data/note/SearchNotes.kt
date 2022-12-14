package com.banana.digitaldiary.data.note

import com.banana.digitaldiary.domain.note.Note
import com.banana.digitaldiary.domain.time.DateTimeUtil

class SearchNotes {

    fun search(notes: List<Note>, query: String): List<Note> {
        if (query.isBlank()) {
            return notes.sortedByDescending { DateTimeUtil.toEpochMillis(it.created)}
        }
        return notes.filter {
            it.title.trim().lowercase().contains(query.lowercase()) ||
            it.content.trim().lowercase().contains(query.lowercase()) ||
            DateTimeUtil.formatDate(it.created).trim().lowercase().contains(query.lowercase())
        }.sortedByDescending { DateTimeUtil.toEpochMillis(it.created) }
    }

}