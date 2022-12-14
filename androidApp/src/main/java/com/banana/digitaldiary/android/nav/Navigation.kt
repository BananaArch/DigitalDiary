package com.banana.digitaldiary.android.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.banana.digitaldiary.android.contact.contact_detail.ContactDetailScreen
import com.banana.digitaldiary.android.contact.contact_list.ContactListScreen
import com.banana.digitaldiary.android.note.note_detail.NoteDetailScreen
import com.banana.digitaldiary.android.note.note_list.NoteListScreen
import com.banana.digitaldiary.android.reminder.reminder_detail.ReminderDetailScreen
import com.banana.digitaldiary.android.reminder.reminder_list.ReminderListScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "note/note_list") {
        composable("note/note_list") {
            NoteListScreen(navController = navController)
        }
        composable(
            route = "note/note_detail/{noteId}",
            arguments = listOf(
                navArgument(name = "noteId") {
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )
        ) {
                backStackEntry ->
            val noteId = backStackEntry.arguments?.getLong("noteId") ?: -1L
            NoteDetailScreen(navController = navController)
        }
        composable("contact/contact_list") {
            ContactListScreen(navController = navController)
        }
        composable(
            "contact/contact_detail/{contactId}",
            arguments = listOf(
                navArgument(name = "contactId") {
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )
        ) {

            backStackEntry ->
            val contactId = backStackEntry.arguments?.getLong("contactId") ?: -1L
            ContactDetailScreen(navController = navController)

        }
        composable("reminder/reminder_list") {
            ReminderListScreen(navController = navController)
        }
        composable(
            "reminder/reminder_detail/{reminderId}",
            arguments = listOf(
                navArgument(name = "reminderId") {
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )
        ) {

                backStackEntry ->
            val reminderId = backStackEntry.arguments?.getLong("reminderId") ?: -1L
            ReminderDetailScreen(navController = navController)

        }
    }
}
