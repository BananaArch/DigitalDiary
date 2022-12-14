package com.banana.digitaldiary.android

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.compose.runtime.Composable
import com.banana.digitaldiary.android.notification.NotificationService
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        appContext = applicationContext
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                NotificationService.NOTIFICATION_CHANNEL_ID,
                NotificationService.NOTIFICATION_NAME,
                NotificationService.NOTIFICATION_IMPORTANCE
            )
            channel.description = NotificationService.NOTIFICATION_DESC
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun deleteNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.deleteNotificationChannel(NotificationService.NOTIFICATION_CHANNEL_ID)
        }
    }

    companion object {

        lateinit var appContext: Context

    }

}