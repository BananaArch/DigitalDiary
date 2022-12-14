package com.banana.digitaldiary.android

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.compose.rememberNavController
import com.banana.digitaldiary.android.nav.BottomNavItem
import com.banana.digitaldiary.android.nav.BottomNavigationBar
import com.banana.digitaldiary.android.nav.Navigation
import com.banana.digitaldiary.android.notification.NotificationService
import com.banana.digitaldiary.domain.time.DateTimeUtil

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            Scaffold(
                bottomBar = {
                    BottomNavigationBar(
                        items = listOf(
                            BottomNavItem(
                                name = "Contacts",
                                route = "contact/contact_list",
                                icon = Icons.Default.Group
                            ),
                            BottomNavItem(
                                name = "Diary",
                                route = "note/note_list",
                                icon = Icons.Default.Book
                            ),
                            BottomNavItem(
                                name = "Schedule",
                                route = "reminder/reminder_list",
                                icon = Icons.Default.DateRange
                            )
                        ),
                        navController = navController,
                        onItemClick = {
                            navController.navigate(it.route)
                        }

                    )
                }
            ) { Navigation(navController = navController) }
        }

    }














}






