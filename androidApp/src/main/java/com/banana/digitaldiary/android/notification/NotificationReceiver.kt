package com.banana.digitaldiary.android.notification

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.banana.digitaldiary.domain.time.DateTimeUtil
import kotlinx.datetime.LocalDateTime

class NotificationReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val service = NotificationService(context)
        val id = intent.getIntExtra(NotificationService.NOTIFICATION_ID_EXTRA, 0)
        val title = intent.getStringExtra(NotificationService.NOTIFICATION_TITLE_EXTRA)!!
        val message = intent.getStringExtra(NotificationService.NOTIFICATION_MESSAGE_EXTRA)
        val start = intent.getLongExtra(NotificationService.NOTIFICATION_START_EXTRA, 0)
        val end = intent.getLongExtra(NotificationService.NOTIFICATION_END_EXTRA, 0)

        service.showNotification(
            id = id,
            title = title,
            message = message
        )
        if (DateTimeUtil.nowEpochMillis() in (start + 1) until end)
            service.scheduleNotification(
                id = id,
                title = title,
                start = DateTimeUtil.toLocalDateTime(start),
                end = DateTimeUtil.toLocalDateTime(end)
            )
    }

}