package com.example.streamwidetechtest.presentation.ui.contact_list

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.streamwidetechtest.R
import com.example.streamwidetechtest.presentation.component.ContactCell
import com.example.streamwidetechtest.presentation.component.SearchField
import com.example.streamwidetechtest.util.UIEvent
import com.example.streamwidetechtest.util.isAllNumbers
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ContactList(
    viewModel: ContactListViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val refreshing by remember {
        mutableStateOf(false)
    }
    val pullRefreshState = rememberPullRefreshState(refreshing, { viewModel.fetchContacts() })

    val state = viewModel.state.value

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Contacts", color = Color(0xFF256A78))
                IconButton(onClick = {/*navigate to add new contact*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.add_contact_icon),
                        contentDescription = "new contact"
                    )
                }
            }
            SearchField(
                widthFraction = .9f,
                placeHolder = stringResource(R.string.search_placeholder)
            ) {
                if (it.isAllNumbers()) {
                    viewModel.searchByPhoneNumber(it)
                } else {
                    viewModel.searchByName(it)
                }
            }
            Box(modifier = Modifier.weight(1f).pullRefresh(pullRefreshState)) {
                LazyColumn {
                    items(state.contactList.size) {
                        with(state.contactList[it]) {
                            ContactCell(name = name, phoneNumber = phoneNumber, photoUri = "")
                        }
                    }
                }
                PullRefreshIndicator(refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
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