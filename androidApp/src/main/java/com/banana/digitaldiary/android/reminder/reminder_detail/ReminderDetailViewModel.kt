package com.banana.digitaldiary.android.reminder.reminder_detail

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.banana.digitaldiary.android.App
import com.banana.digitaldiary.android.notification.NotificationService
import com.banana.digitaldiary.domain.reminder.Reminder
import com.banana.digitaldiary.domain.reminder.ReminderDataSource
import com.banana.digitaldiary.domain.time.DateTimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalTime
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject
import kotlin.system.exitProcess

@HiltViewModel
class ReminderDetailViewModel @Inject constructor (
    private val reminderDataSource: ReminderDataSource,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val reminderTitle = savedStateHandle.getStateFlow("reminderTitle", "")
    private val reminderStartDate = savedStateHandle.getStateFlow("reminderStartDate", LocalDate.now())
    private val reminderStartTime = savedStateHandle.getStateFlow("reminderStartTime", LocalTime.MIDNIGHT)
    private val reminderEndDate = savedStateHandle.getStateFlow("reminderEndDate", LocalDate.now().plusMonths(1))
    private val reminderEndTime = savedStateHandle.getStateFlow("reminderEndTime", LocalTime.MIDNIGHT)

    val state = combine(
        reminderTitle,
        reminderStartDate,
        reminderStartTime,
        reminderEndDate,
        reminderEndTime

    ) {
        reminderTitle, reminderStartDate, reminderStartTime, reminderEndDate, reminderEndTime  ->
        ReminderDetailState(
            reminderTitle = reminderTitle,
            reminderStartDate = reminderStartDate,
            reminderStartTime = reminderStartTime,
            reminderEndDate = reminderEndDate,
            reminderEndTime = reminderEndTime
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ReminderDetailState())

    private val _hasReminderBeenChanged = MutableStateFlow(false)
    val hasReminderBeenChanged = _hasReminderBeenChanged.asStateFlow()
    private val _hasReminderBeenSaved = MutableStateFlow(false)
    val hasReminderBeenSaved = _hasReminderBeenSaved.asStateFlow()

    private var existingReminderId: Long? = null

    init {

        savedStateHandle.get<Long>("reminderId")?.let {
                existingReminderId ->

            if (existingReminderId == -1L)
                return@let


            this.existingReminderId = existingReminderId
            viewModelScope.launch {
                reminderDataSource.getReminderById(existingReminderId)?.let {
                        reminder ->

                    savedStateHandle["reminderTitle"] = reminder.title
                    savedStateHandle["reminderStartDate"] = reminder.start.toJavaLocalDateTime().toLocalDate()
                    savedStateHandle["reminderStartTime"] = reminder.start.toJavaLocalDateTime().toLocalTime()
                    savedStateHandle["reminderEndDate"] = reminder.end.toJavaLocalDateTime().toLocalDate()
                    savedStateHandle["reminderEndTime"] = reminder.end.toJavaLocalDateTime().toLocalTime()
                }

            }
        }
    }

    fun onReminderTitleChanged(text: String) {
        savedStateHandle["reminderTitle"] = text
        _hasReminderBeenChanged.value = true
    }
    fun onReminderStartDateChanged(startDate: LocalDate) {
        savedStateHandle["reminderStartDate"] = startDate
        _hasReminderBeenChanged.value = true
    }
    fun onReminderStartTimeChanged(startTime: LocalTime) {
        savedStateHandle["reminderStartTime"] = startTime
        _hasReminderBeenChanged.value = true
    }
    fun onReminderEndDateChanged(endDate: LocalDate) {
        savedStateHandle["reminderEndDate"] = endDate
        _hasReminderBeenChanged.value = true
    }
    fun onReminderEndTimeChanged(endTime: LocalTime) {
        savedStateHandle["reminderEndTime"] = endTime
        _hasReminderBeenChanged.value = true
    }


    fun saveReminder() {
        viewModelScope.launch {

            if (_hasReminderBeenChanged.value) {

                val notificationService = NotificationService(App.appContext)
                val startLocalDateTime = LocalDateTime(reminderStartDate.value.toKotlinLocalDate(), reminderStartTime.value.toKotlinLocalTime())
                val endLocalDateTime = LocalDateTime(reminderEndDate.value.toKotlinLocalDate(), reminderEndTime.value.toKotlinLocalTime())
                val reminderTitle = reminderTitle.value

                reminderDataSource.insertReminder(
                    Reminder(
                        id = existingReminderId,
                        title = reminderTitle,
                        start = startLocalDateTime,
                        end = endLocalDateTime
                    )
                )

                val id =
                    if (existingReminderId == null)
                        reminderDataSource.getLastInsertedReminderId()
                    else
                        existingReminderId

                notificationService.scheduleNotification(
                    id = id!!.toInt(),
                    title = reminderTitle,
                    start = startLocalDateTime,
                    end = endLocalDateTime
                )
            }

            _hasReminderBeenSaved.value = true
        }
    }
}