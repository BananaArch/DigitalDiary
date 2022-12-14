package com.banana.digitaldiary.android.reminder.reminder_detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.FirstPage
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.banana.digitaldiary.domain.time.DateTimeUtil
import com.banana.digitaldiary.presentation.ashGrayHex
import com.banana.digitaldiary.presentation.deepSpaceSparkleHex
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalTime
import java.time.LocalTime

@Composable
fun ReminderDetailScreen(
    navController: NavController,
    viewModel: ReminderDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val hasReminderBeenChanged by viewModel.hasReminderBeenChanged.collectAsState()
    val hasReminderBeenSaved by viewModel.hasReminderBeenSaved.collectAsState()

    LaunchedEffect(key1 = hasReminderBeenSaved) {
        if (hasReminderBeenSaved) {
            navController.popBackStack()
        }
    }

    Scaffold(

        floatingActionButton = {
            FloatingActionButton(
                onClick = viewModel::saveReminder,
                backgroundColor = Color.Black,
                modifier = Modifier.padding(bottom = 64.dp)
            ) {
                Icon(
                    imageVector =
                        if (hasReminderBeenChanged)
                            Icons.Default.Done
                        else
                            Icons.Default.FirstPage,
                    contentDescription = "Save Reminder",
                    tint = Color.White
                )
            }
        }


    ) {
            padding ->

        val startDateDialogState = rememberMaterialDialogState()
        val startTimeDialogState = rememberMaterialDialogState()
        val endDateDialogState = rememberMaterialDialogState()
        val endTimeDialogState = rememberMaterialDialogState()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
//                .padding(16.dp)

        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp)
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Edit Schedule",
                    fontWeight = FontWeight.Light,
                    color = Color(deepSpaceSparkleHex),
                    fontSize = 30.sp
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {

                OutlinedTextField(
                    value = state.reminderTitle,
                    label = {
                        Text(
                            text = "Title"
                        )
                    },
                    onValueChange = {
                        viewModel.onReminderTitleChanged(it)
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(25.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors (
                        focusedLabelColor = Color(deepSpaceSparkleHex),
                        cursorColor = Color(deepSpaceSparkleHex),
                        focusedBorderColor = Color(deepSpaceSparkleHex),
                        unfocusedBorderColor = Color(ashGrayHex)
                    ),
                    modifier = Modifier.fillMaxWidth()

                )


                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    )
                    {
                        Button(
                            shape = RoundedCornerShape(25.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(deepSpaceSparkleHex)
                            ),
                            onClick = {
                                startDateDialogState.show()
                            },
                            modifier = Modifier
                                .height(48.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Edit Start Date",
                                color = Color.White
                            )
                        }
                        Text(
                            DateTimeUtil.formatDate(LocalDateTime(state.reminderStartDate.toKotlinLocalDate(), state.reminderStartTime.toKotlinLocalTime()))
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    )
                    {
                        Button(
                            shape = RoundedCornerShape(25.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(deepSpaceSparkleHex)
                            ),
                            onClick = {
                                endDateDialogState.show()
                            },
                            modifier = Modifier
                                .height(48.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "Edit End Date",
                                color = Color.White
                            )
                        }
                        Text(
                            DateTimeUtil.formatDate(LocalDateTime(state.reminderEndDate.toKotlinLocalDate(), state.reminderEndTime.toKotlinLocalTime()))
                        )
                    }

                }



            }
            

        }





        /*
        For calendar & clocks
         */



        // start calendar

        MaterialDialog(
            dialogState = startDateDialogState,
            shape = RoundedCornerShape(5.dp),
            buttons = {
                positiveButton(
                    text = "OK",
                    textStyle = TextStyle(
                        color = Color(deepSpaceSparkleHex)
                    )
                )
                negativeButton(
                    text = "CANCEL",
                    textStyle = TextStyle(
                        color = Color(deepSpaceSparkleHex)
                    )
                )
            },
            properties = DialogProperties(
                dismissOnClickOutside = true,

            ),

        ) {
            datepicker(
                title = "SELECT DATE",
                initialDate = state.reminderStartDate,
                colors = DatePickerDefaults.colors(
                    headerBackgroundColor = Color(ashGrayHex),
                    headerTextColor = Color.Black,
                    calendarHeaderTextColor = Color(deepSpaceSparkleHex),
                    dateActiveBackgroundColor = Color(deepSpaceSparkleHex),
                ),
                allowedDateValidator = {
                    it <= state.reminderEndDate
                }
            ) {
                viewModel.onReminderStartDateChanged(it)
                startTimeDialogState.show()
            }
        }














        // start clock

        MaterialDialog(
            dialogState = startTimeDialogState,
            shape = RoundedCornerShape(5.dp),
            buttons = {
                positiveButton(
                    text = "OK",
                    textStyle = TextStyle(
                        color = Color(deepSpaceSparkleHex)
                    )
                )
                negativeButton(
                    text = "CANCEL",
                    textStyle = TextStyle(
                        color = Color(deepSpaceSparkleHex)
                    )
                )
            },
            properties = DialogProperties(
                dismissOnClickOutside = true,

                ),

            ) {
            timepicker(
                title = "SELECT TIME",
                initialTime = state.reminderStartTime,
                colors = TimePickerDefaults.colors(
                    activeBackgroundColor = Color(deepSpaceSparkleHex),
                    inactiveBackgroundColor = Color(ashGrayHex),
                    inactivePeriodBackground = Color(ashGrayHex),
                    selectorColor = Color(deepSpaceSparkleHex)
                ),
                timeRange =
                    if (state.reminderStartDate == state.reminderEndDate)
                        LocalTime.MIN..state.reminderEndTime
                    else
                        LocalTime.MIN..LocalTime.MAX

            ) {
                viewModel.onReminderStartTimeChanged(it)
            }
        }
















        // end calendar

        MaterialDialog(
            dialogState = endDateDialogState,
            shape = RoundedCornerShape(5.dp),
            buttons = {
                positiveButton(
                    text = "OK",
                    textStyle = TextStyle(
                        color = Color(deepSpaceSparkleHex)
                    )
                )
                negativeButton(
                    text = "CANCEL",
                    textStyle = TextStyle(
                        color = Color(deepSpaceSparkleHex)
                    )
                )
            },
            properties = DialogProperties(
                dismissOnClickOutside = true,

                ),

            ) {
            datepicker(
                title = "SELECT DATE",
                initialDate = state.reminderEndDate,
                colors = DatePickerDefaults.colors(
                    headerBackgroundColor = Color(ashGrayHex),
                    headerTextColor = Color.Black,
                    calendarHeaderTextColor = Color(deepSpaceSparkleHex),
                    dateActiveBackgroundColor = Color(deepSpaceSparkleHex),
                ),
                allowedDateValidator = {
                    it >= state.reminderStartDate
                }
            ) {
                viewModel.onReminderEndDateChanged(it)
                endTimeDialogState.show()
            }
        }










        // end clock


        MaterialDialog(
            dialogState = endTimeDialogState,
            shape = RoundedCornerShape(5.dp),
            buttons = {
                positiveButton(
                    text = "OK",
                    textStyle = TextStyle(
                        color = Color(deepSpaceSparkleHex)
                    )
                )
                negativeButton(
                    text = "CANCEL",
                    textStyle = TextStyle(
                        color = Color(deepSpaceSparkleHex)
                    )
                )
            },
            properties = DialogProperties(
                dismissOnClickOutside = true,

                ),

            ) {
            timepicker(
                title = "SELECT TIME",
                initialTime = state.reminderEndTime,
                colors = TimePickerDefaults.colors(
                    activeBackgroundColor = Color(deepSpaceSparkleHex),
                    inactiveBackgroundColor = Color(ashGrayHex),
                    inactivePeriodBackground = Color(ashGrayHex),
                    selectorColor = Color(deepSpaceSparkleHex)
                ),
                timeRange =
                    if (state.reminderStartDate == state.reminderEndDate)
                        state.reminderStartTime..LocalTime.MAX
                    else
                        LocalTime.MIN..LocalTime.MAX

            ) {
                viewModel.onReminderEndTimeChanged(it)
            }
        }

    }

}

