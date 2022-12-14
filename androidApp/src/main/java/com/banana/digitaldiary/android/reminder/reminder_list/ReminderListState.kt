package com.banana.digitaldiary.android.reminder.reminder_list

import com.banana.digitaldiary.domain.reminder.Reminder

data class ReminderListState (
    val reminders: List<Reminder> = emptyList(),
    val searchText: String = "",
    val isSearchActive: Boolean = false
)