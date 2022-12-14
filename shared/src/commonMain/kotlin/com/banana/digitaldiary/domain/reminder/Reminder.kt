package com.banana.digitaldiary.domain.reminder

import com.banana.digitaldiary.domain.time.DateTimeUtil
import com.banana.digitaldiary.presentation.*
import kotlinx.datetime.LocalDateTime
import kotlin.math.abs

data class Reminder(
    val id: Long?,
    val title: String,
    val start: LocalDateTime,
    val end: LocalDateTime
) {
    companion object {
        private val colors = listOf(ashGrayHex, slimyGreenHex, greenRYBHex, yellowGreenHex, citrineHex, sunglowHex, marigoldHex, ochreHex, burntOrangeHex, sinopiaHex)

        fun getColor(start: LocalDateTime, end: LocalDateTime): Long {

            if (DateTimeUtil.now() > end)
                return ashGrayHex

            val difference: Long =
                // already started
                if (DateTimeUtil.now() > start)
                    abs(DateTimeUtil.nowEpochMillis() - DateTimeUtil.toEpochMillis(end))
                // hasn't started
                else
                    abs(DateTimeUtil.nowEpochMillis() - DateTimeUtil.toEpochMillis(start))

            // 5 mins
            if (difference < 5 * 60 * 1000)
                return sinopiaHex
            // 15 mins
            if (difference < 15 * 60 * 1000)
                return burntOrangeHex
            // 1 hour
            if (difference < 1 * 60 * 60 * 1000)
                return ochreHex
            // 3 hours
            if (difference < 3 * 60 * 60 * 1000)
                return marigoldHex
            // 8 hours
            if (difference < 8 * 60 * 60 * 1000)
                return sunglowHex
            // 1 day
            if (difference < 1 * 24 * 60 * 60 * 1000)
                return citrineHex
            // 3 days
            if (difference < 3 * 24 * 60 * 60 * 1000)
                return yellowGreenHex
            // 1 week
            if (difference < 7 * 24 * 60 * 60 * 1000)
                return greenRYBHex

            return slimyGreenHex

        }




    }

}

