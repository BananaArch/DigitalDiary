package com.banana.digitaldiary.android.note.note_list

import com.banana.digitaldiary.domain.note.Note

data class NoteListState(
    val notes: List<Note> = emptyList(),
    val searchText: String = "",
    val isSearchActive: Boolean = false
)
