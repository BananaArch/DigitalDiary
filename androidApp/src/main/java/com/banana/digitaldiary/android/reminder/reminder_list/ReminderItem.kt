package com.banana.digitaldiary.android.reminder.reminder_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.banana.digitaldiary.domain.reminder.Reminder
import com.banana.digitaldiary.domain.text.TextUtil
import com.banana.digitaldiary.domain.time.DateTimeUtil
import com.banana.digitaldiary.presentation.deepSpaceSparkleHex

@Composable
fun ReminderItem(
    reminder: Reminder,
    backgroundColor: Color,
    onReminderClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(backgroundColor)
            .clickable { onReminderClick() }
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = TextUtil.formatString(reminder.title),
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
            )
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Reminder",
                tint = Color(deepSpaceSparkleHex),
                modifier = Modifier
                    .clickable{
                        onDeleteClick()
                    }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = DateTimeUtil.formatDate(reminder.start),
            fontWeight = FontWeight.Light,
            fontSize = 20.sp
        )
        Text(
            text = DateTimeUtil.formatDate(reminder.end),
            fontWeight = FontWeight.Light,
            fontSize = 20.sp
        )
    }
}