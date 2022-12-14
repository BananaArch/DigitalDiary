package com.banana.digitaldiary.data.reminder

import com.banana.digitaldiary.database.AppDatabase
import com.banana.digitaldiary.data.note.toNote
import com.banana.digitaldiary.domain.note.Note
import com.banana.digitaldiary.domain.reminder.Reminder
import com.banana.digitaldiary.domain.reminder.ReminderDataSource
import com.banana.digitaldiary.domain.time.DateTimeUtil
import kotlinx.datetime.LocalDateTime


class SqlDelightReminderDataSource(database: AppDatabase): ReminderDataSource {

    private val queries = database.appQueries

    override suspend fun insertReminder(reminder: Reminder) {
        queries.insertReminder(
            id = reminder.id,
            title = reminder.title,
            start = DateTimeUtil.toEpochMillis(reminder.start),
            end = DateTimeUtil.toEpochMillis(reminder.end)
        )
    }


    override suspend fun getReminderById(id: Long): Reminder? {
        return queries
            .getReminderById(id)
            .executeAsOneOrNull()
            ?.toReminder()
    }

    override suspend fun getAllReminders(): List<Reminder> {
        return queries
            .getAllReminders()
            .executeAsList()
            .map{ it.toReminder() }
    }

    override suspend fun deleteReminderById(id: Long) {
        queries.deleteReminderById(id)
    }

    override suspend fun getLastInsertedReminderId(): Long? {
        return queries
            .getLastReminderId()
            .executeAsOneOrNull()
    }
}