package com.banana.digitaldiary.android.contact.contact_list

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
import com.banana.digitaldiary.domain.contact.Contact
import com.banana.digitaldiary.domain.text.TextUtil
import com.banana.digitaldiary.presentation.deepSpaceSparkleHex

@Composable
fun ContactItem(
    contact: Contact,
    backgroundColor: Color,
    onContactClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(backgroundColor)
            .clickable { onContactClick() }
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = TextUtil.formatString(contact.name),
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
            )
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete Contact",
                tint = Color(deepSpaceSparkleHex),
                modifier = Modifier
                    .clickable{
                        onDeleteClick()
                    }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = TextUtil.formatString(contact.phone),
            fontWeight = FontWeight.Light,
            fontSize = 20.sp
        )
        Text(
            text = contact.email,
            fontWeight = FontWeight.ExtraLight,
            fontSize = 20.sp
        )
    }
}