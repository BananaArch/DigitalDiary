package com.banana.digitaldiary.android.reminder.reminder_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.banana.digitaldiary.android.contact.contact_list.HideableSearchTextField
import com.banana.digitaldiary.domain.reminder.Reminder
import com.banana.digitaldiary.presentation.deepSpaceSparkleHex
import com.banana.digitaldiary.presentation.orangeRedHex

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReminderListScreen (
    navController: NavController,
    viewModel: ReminderListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.loadReminders()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("reminder/reminder_detail/-1L")
                },
                backgroundColor = Color.Black,
                modifier = Modifier
                    .padding(bottom = 64.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Reminder",
                    tint = Color.White
                )
            }
        }
    ) { padding ->
        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)

        ) {

            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                HideableSearchTextField(
                    text = state.searchText,
                    isSearchActive = state.isSearchActive,
                    onTextChange = viewModel::onSearchTextChange,
                    onSearchClick = viewModel::onToggleSearch,
                    onCloseClick = viewModel::onToggleSearch,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                )
                this@Column.AnimatedVisibility(
                    visible = !state.isSearchActive,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Text(
                        text = "Schedule",
                        color = Color(deepSpaceSparkleHex),
                        fontWeight = FontWeight.Light,
                        fontSize = 30.sp

                    )
                }

            }
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(

                    items = state.reminders,
                    key = { it.id!! }

                ) { reminder ->
                    ReminderItem(
                        reminder = reminder,
                        backgroundColor = Color(Reminder.getColor(reminder.start, reminder.end)),
                        onReminderClick = {
                            navController.navigate("reminder/reminder_detail/${reminder.id}")
                        },
                        onDeleteClick = {
                            viewModel.deleteReminderById(reminder.id!!)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .animateItemPlacement()
                    )

                }

            }
            Spacer(
                modifier = Modifier.padding(bottom = 50.dp)
            )
        }


    }

}