package com.banana.digitaldiary.android.contact.contact_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.banana.digitaldiary.presentation.ashGrayHex
import com.banana.digitaldiary.presentation.deepSpaceSparkleHex
import com.banana.digitaldiary.presentation.orangeRedHex

@Composable
fun HideableSearchTextField(
    text: String,
    isSearchActive: Boolean,
    onTextChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        AnimatedVisibility(
            visible = isSearchActive,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = onTextChange,
                shape = RoundedCornerShape(100.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors (
                    cursorColor = Color(deepSpaceSparkleHex),
                    focusedBorderColor = Color(deepSpaceSparkleHex),
                    unfocusedBorderColor = Color(ashGrayHex)
                ),
                placeholder = {
                    Text(text = "Search")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(end = 48.dp)
            )
        }
        AnimatedVisibility(
            visible = isSearchActive,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            IconButton(onClick = onCloseClick) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Search",
                    tint = Color(deepSpaceSparkleHex)
                )
            }
        }
        AnimatedVisibility(
            visible = !isSearchActive,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            IconButton(onClick = onSearchClick) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Open Search",
                    tint = Color(deepSpaceSparkleHex)
                )
            }
        }
    }
}

