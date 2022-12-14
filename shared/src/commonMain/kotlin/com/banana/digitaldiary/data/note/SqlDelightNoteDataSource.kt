package com.banana.digitaldiary.data.note

import com.banana.digitaldiary.database.AppDatabase
import com.banana.digitaldiary.domain.note.Note
import com.banana.digitaldiary.domain.note.NoteDataSource
import com.banana.digitaldiary.domain.time.DateTimeUtil

class SqlDelightNoteDataSource(database: AppDatabase): NoteDataSource {

    private val queries = database.appQueries

    override suspend fun insertNote(note: Note) {
        queries.insertNote(
            id = note.id,
            title = note.title,
            content = note.content,
            colorHex = note.colorHex,
            created = DateTimeUtil.toEpochMillis(note.created)
        )
    }

    override suspend fun getNoteById(id: Long): Note? {
        return queries
            .getNoteById(id)
            .executeAsOneOrNull()
            ?.toNote()
    }

    override suspend fun getAllNotes(): List<Note> {
        return queries
            .getAllNotes()
            .executeAsList()
            .map {
                it.toNote()
            }
        return emptyList()
    }

    override suspend fun deleteNoteById(id: Long) {
        queries.deleteNoteById(id)
    }
}