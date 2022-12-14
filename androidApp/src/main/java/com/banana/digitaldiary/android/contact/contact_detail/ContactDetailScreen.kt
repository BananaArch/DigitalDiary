package com.banana.digitaldiary.android.contact.contact_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.FirstPage
import androidx.compose.material.icons.filled.MenuOpen
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.banana.digitaldiary.presentation.ashGrayHex
import com.banana.digitaldiary.presentation.deepSpaceSparkleHex
import kotlinx.coroutines.launch

@Composable
fun ContactDetailScreen(
    navController: NavController,
    viewModel: ContactDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val hasContactBeenChanged by viewModel.hasContactBeenChanged.collectAsState()
    val hasContactBeenSaved by viewModel.hasContactBeenSaved.collectAsState()

    LaunchedEffect(key1 = hasContactBeenSaved) {
        if (hasContactBeenSaved) {
            navController.popBackStack()
        }
    }

    Scaffold(

        floatingActionButton = {
            FloatingActionButton(
                onClick = viewModel::saveContact,
                backgroundColor = Color.Black,
                modifier = Modifier.padding(bottom = 64.dp)
            ) {
                Icon(
                    imageVector =
                        if (hasContactBeenChanged)
                            Icons.Default.Done
                        else
                            Icons.Default.FirstPage,
                    contentDescription = "Save Contact",
                    tint = Color.White
                )
            }
        }


    ) {
        padding ->
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
                    .background(Color(state.contactColor))
                    .height(90.dp)
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Edit Contacts",
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
                    value = state.contactName,
                    label = {
                        Text(
                            text = "Name"
                        )
                    },
                    onValueChange = {
                        viewModel.onContactNameChanged(it)
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
                OutlinedTextField(
                    value = state.contactPhone,
                    label = {
                        Text(
                            text = "Phone"
                        )
                    },
                    onValueChange = {
                        viewModel.onContactPhoneChanged(it)
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
                OutlinedTextField(
                    value = state.contactEmail,
                    label = {
                        Text(
                            text = "Email"
                        )
                    },
                    onValueChange = {
                        viewModel.onContactEmailChanged(it)
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


            }

        }
    }

}