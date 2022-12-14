package com.banana.digitaldiary.data.reminder

import com.banana.digitaldiary.domain.note.Note
import com.banana.digitaldiary.domain.reminder.Reminder
import database.ReminderEntity
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun ReminderEntity.toReminder(): Reminder {
    return Reminder(
        id = id,
        title = title,
        start = Instant
            .fromEpochMilliseconds(start)
            .toLocalDateTime(TimeZone.currentSystemDefault()),
        end = Instant
            .fromEpochMilliseconds(end)
            .toLocalDateTime(TimeZone.currentSystemDefault())
    )
}