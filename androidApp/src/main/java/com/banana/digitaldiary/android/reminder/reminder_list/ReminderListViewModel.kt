package com.banana.digitaldiary.android.reminder.reminder_list

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.banana.digitaldiary.domain.contact.Contact
import com.banana.digitaldiary.data.reminder.SearchReminders
import com.banana.digitaldiary.domain.reminder.Reminder
import com.banana.digitaldiary.domain.reminder.ReminderDataSource
import com.banana.digitaldiary.domain.time.DateTimeUtil
import com.banana.digitaldiary.presentation.orangeRedHex
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ReminderListViewModel @Inject constructor (
    private val reminderDataSource: ReminderDataSource,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val searchReminders = SearchReminders()
    private val reminders = savedStateHandle.getStateFlow("reminders", emptyList<Reminder>())
    private val searchText = savedStateHandle.getStateFlow("searchText", "")
    private val isSearchActive = savedStateHandle.getStateFlow("isSearchActive", false)

    val state = combine(reminders, searchText, isSearchActive) {
            reminders, searchText, isSearchActive ->
        ReminderListState(
            reminders = searchReminders.search(reminders, searchText),
            searchText = searchText,
            isSearchActive = isSearchActive
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ReminderListState())

    fun loadReminders() {
        viewModelScope.launch {
            savedStateHandle["reminders"] = reminderDataSource.getAllReminders()
        }
    }

    fun onSearchTextChange(text: String) {
        savedStateHandle["searchText"] = text
    }

    fun onToggleSearch() {
        savedStateHandle["isSearchActive"] = !isSearchActive.value
        if (!isSearchActive.value) {
            savedStateHandle["searchText"] = ""
        }
    }

    fun deleteReminderById(id: Long) {
        viewModelScope.launch {
            reminderDataSource.deleteReminderById(id)
            loadReminders()
        }
    }
}