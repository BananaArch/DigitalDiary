package com.banana.digitaldiary.android.reminder.reminder_detail

import com.banana.digitaldiary.domain.time.DateTimeUtil
import java.time.LocalDate
import java.time.LocalTime

data class ReminderDetailState(
    val reminderTitle: String = "",
    val reminderStartDate: LocalDate = LocalDate.now(),
    val reminderStartTime: LocalTime = LocalTime.MIDNIGHT,
    val reminderEndDate: LocalDate = LocalDate.now().plusMonths(1),
    val reminderEndTime: LocalTime = LocalTime.NOON
)

