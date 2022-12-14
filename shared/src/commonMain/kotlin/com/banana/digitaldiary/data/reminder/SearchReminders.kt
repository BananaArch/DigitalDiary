package com.banana.digitaldiary.data.reminder

import com.banana.digitaldiary.domain.reminder.Reminder
import com.banana.digitaldiary.domain.time.DateTimeUtil
import com.banana.digitaldiary.presentation.*
import kotlin.math.abs

class SearchReminders {
    fun search(reminders: List<Reminder>, query: String): List<Reminder> {
        if (query.isBlank()) {
            return reminders.sortedBy {

                //already finished
                if (DateTimeUtil.now() > it.end)
                    Long.MAX_VALUE
                // already start
                else if (DateTimeUtil.now() > it.start)
                    abs(DateTimeUtil.nowEpochMillis() - DateTimeUtil.toEpochMillis(it.end))
                // hasn't started
                else
                    abs(DateTimeUtil.nowEpochMillis() - DateTimeUtil.toEpochMillis(it.start))


            }
        }
        return reminders.filter {
            it.title.trim().lowercase().contains(query.lowercase()) ||
            DateTimeUtil.formatDate(it.start).trim().lowercase().contains(query.lowercase()) ||
            DateTimeUtil.formatDate(it.end).trim().lowercase().contains(query.lowercase())


        }.sortedBy {

            //already finished
            if (DateTimeUtil.now() > it.end)
                Long.MAX_VALUE
            // already start
            else if (DateTimeUtil.now() > it.start)
                abs(DateTimeUtil.nowEpochMillis() - DateTimeUtil.toEpochMillis(it.end))
            // hasn't started
            else
                abs(DateTimeUtil.nowEpochMillis() - DateTimeUtil.toEpochMillis(it.start))
        }
    }
}