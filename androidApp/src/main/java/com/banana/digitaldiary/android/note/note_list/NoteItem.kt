package com.banana.digitaldiary.android.note.note_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.banana.digitaldiary.domain.note.Note
import com.banana.digitaldiary.domain.time.DateTimeUtil
import com.banana.digitaldiary.presentation.deepSpaceSparkleHex

@Composable
fun NoteItem(
    note: Note,
    backgroundColor: Color,
    onNoteClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val formattedDate = remember(note.created) {
        DateTimeUtil.formatDate(note.created)
    }
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(5.dp))
            .background(backgroundColor)
            .clickable {
                onNoteClick()
            }
            .padding(16.dp)
    ) {
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text (
                text = note.title,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
            )
            Icon(

                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Note",
                tint = Color(deepSpaceSparkleHex),
//                    if (Color(note.colorHex).luminance() < .5)
//                        Color(ashGrayHex)
//                    else
//                        Color(deepSpaceSparkleHex),
                modifier = Modifier
                    .clickable(MutableInteractionSource(), null) {
                        onDeleteClick()
                    }
            )
        }
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        Text(
            text = note.content,
            fontWeight = FontWeight.Light
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        Text(
            text = formattedDate,
            modifier = Modifier.align(Alignment.End)
        )
    }
}