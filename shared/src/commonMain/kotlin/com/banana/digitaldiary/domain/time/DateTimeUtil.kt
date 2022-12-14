package com.banana.digitaldiary.domain.time

import kotlinx.datetime.*
import kotlin.time.Duration

object DateTimeUtil {

    fun now(): LocalDateTime {
        return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    }

    fun nowEpochMillis(): Long {
        return now().toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
    }

    fun toEpochMillis(dateTime: LocalDateTime): Long {
        return dateTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
    }

    fun toLocalDateTime(epochMillis: Long): LocalDateTime {
        return Instant.fromEpochMilliseconds(epochMillis).toLocalDateTime(TimeZone.currentSystemDefault());
    }

    fun formatDate(dateTime: LocalDateTime): String {
        val month = dateTime.month.name.lowercase().take(3).replaceFirstChar {
            it.uppercase()
        }

        val day = dateTime.dayOfMonth
        val year = dateTime.year

        val period =
            if (dateTime.hour < 12)
                "AM"
            else
                "PM"

        val hour =
            // military time to standard time
            if (dateTime.hour > 12)
                dateTime.hour - 12
            else if (dateTime.hour == 0)
                12
            else
                dateTime.hour

        val minute =
            if (dateTime.minute < 10)
                "0${dateTime.minute}"
            else
                dateTime.minute


        /*
        SPECIAL CASES
        TOMMOROW
        TODAY
        YESTERDAY
         */


        val date = LocalDate(dateTime.year, dateTime.monthNumber, dateTime.dayOfMonth)
        val dateNow = LocalDate(now().year, now().monthNumber, now().dayOfMonth)

        val dateString =
            if (dateNow.plus(1, DateTimeUnit.DAY) == date)
                "Tommorow, "
            else if (dateNow == date)
                "Today, "
            else if (dateNow.minus(1, DateTimeUnit.DAY) == date)
                "Yesterday, "
            else
                buildString {
                    append(month)
                    append(" ")
                    append(day)
                    append(", ")
                    append(year)
                    append(" ")
                }

        val timeString =
//            if (dateTime.hour == 12 && dateTime.minute == 0 && dateTime.second == 0)
//                "Noon"
//            else if (dateTime.hour == 0 && dateTime.minute == 0 && dateTime.second == 0)
//                "Midnight"
//            else
            buildString {
                append(hour)
                append(":")
                append(minute)
                append(" ")
                append(period)
            }

        return buildString {
            append(dateString)
            append(timeString)
        }



    }
}