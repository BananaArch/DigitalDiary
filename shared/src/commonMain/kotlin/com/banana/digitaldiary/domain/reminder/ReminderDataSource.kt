package com.banana.digitaldiary.domain.reminder

interface ReminderDataSource {
    suspend fun insertReminder(reminder: Reminder)
    suspend fun getReminderById(id: Long): Reminder?
    suspend fun getAllReminders(): List<Reminder>
    suspend fun deleteReminderById(id: Long)
    suspend fun getLastInsertedReminderId(): Long?
}