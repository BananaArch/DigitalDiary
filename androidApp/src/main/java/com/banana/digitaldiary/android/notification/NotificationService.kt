package com.banana.digitaldiary.android.notification

import android.app.*
import android.app.Notification
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.banana.digitaldiary.android.R
import com.banana.digitaldiary.domain.time.DateTimeUtil
import kotlinx.datetime.LocalDateTime
import kotlin.system.exitProcess

class NotificationService(
    private val context: Context,
) {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification(id: Int, title: String, message: String?) {

        val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentTitle(title)
            .setContentText(message)
            .build()

        notificationManager.notify(
            id,
            notification
        )
    }

    fun scheduleNotification(id: Int, title: String, start: LocalDateTime, end: LocalDateTime) {
        val intent = Intent(context, NotificationReceiver::class.java)

        val localDateTime =
            if (start > DateTimeUtil.now())
                start
            else
                end


        intent.putExtra(NOTIFICATION_ID_EXTRA, id)
        intent.putExtra(NOTIFICATION_TITLE_EXTRA, title)
        intent.putExtra(NOTIFICATION_MESSAGE_EXTRA, DateTimeUtil.formatDate(localDateTime))
        intent.putExtra(NOTIFICATION_START_EXTRA, DateTimeUtil.toEpochMillis(start))
        intent.putExtra(NOTIFICATION_END_EXTRA, DateTimeUtil.toEpochMillis(end))


        val pendingIntent = PendingIntent.getBroadcast(
            context,
            id,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val time = DateTimeUtil.toEpochMillis(localDateTime)

        val alarmManager = context.getSystemService(Application.ALARM_SERVICE) as AlarmManager
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
    }


    companion object {
        const val NOTIFICATION_CHANNEL_ID = "channel1"
        const val NOTIFICATION_NAME = "Schedule"
        const val NOTIFICATION_DESC = "Keep track of your schedule!"
        const val NOTIFICATION_IMPORTANCE = NotificationManager.IMPORTANCE_HIGH
        const val NOTIFICATION_ID_EXTRA = "idExtra"
        const val NOTIFICATION_TITLE_EXTRA = "titleExtra"
        const val NOTIFICATION_MESSAGE_EXTRA = "messageExtra"
        const val NOTIFICATION_START_EXTRA = "startExtra"
        const val NOTIFICATION_END_EXTRA = "endExtra"

    }

}