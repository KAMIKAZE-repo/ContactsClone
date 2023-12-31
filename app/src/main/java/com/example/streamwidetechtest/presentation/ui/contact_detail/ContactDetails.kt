package com.example.streamwidetechtest.presentation.ui.contact_detail

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.streamwidetechtest.R
import com.example.streamwidetechtest.presentation.component.ContactDetailRow
import com.example.streamwidetechtest.util.UIEvent
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ContactDetails(
    navController: NavController,
    contactId: String,
    viewModel: ContactDetailViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = true) {
        viewModel.fetchContactDetails(contactId.toLong())
    }

    val scaffoldState = rememberScaffoldState()
    val state = viewModel.state.value

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .weight(1f), contentAlignment = Alignment.BottomStart
            ) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = if (state.data?.photoUri != null) {
                        rememberImagePainter(
                            data = state.data!!.photoUri,
                        )
                    } else {
                        painterResource(id = R.drawable.placeholder_person)
                    },
                    contentDescription = "contact image",
                    contentScale = ContentScale.FillBounds
                )
                IconButton(modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp), onClick = { navController.popBackStack() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.back_arrow_icon),
                        contentDescription = "back to contacts list",
                        tint = Color.White
                    )
                }
                state.data?.let { it1 ->
                    Text(
                        modifier = Modifier.padding(
                            start = 16.dp,
                            bottom = 16.dp
                        ), text = it1.name, color = Color.White
                    )
                }
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp, vertical = 16.dp)
            ) {
                ContactDetailRow(
                    title = state.data?.phoneNumber,
                    icon = R.drawable.phone_number_icon
                )
                Spacer(modifier = Modifier.height(16.dp))
                ContactDetailRow(title = state.data?.email, icon = R.drawable.email_icon)
            }
        }
        LaunchedEffect(key1 = true) {
            viewModel.eventFlow.collectLatest { event ->
                when (event) {
                    is UIEvent.ShowSnackbar -> {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = event.message
                        )
                    }
                }
            }
        }
    }
}
